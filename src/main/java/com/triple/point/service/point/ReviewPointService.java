package com.triple.point.service.point;

import com.triple.point.domain.history.PointType;
import com.triple.point.domain.history.ReviewHistory;
import com.triple.point.domain.history.repository.ReviewHistoryRepository;
import com.triple.point.domain.point.Point;
import com.triple.point.domain.point.repository.PointRepository;
import com.triple.point.domain.review.Review;
import com.triple.point.domain.review.repository.ReviewRepository;
import com.triple.point.dto.event.ReviewEventRequest;
import com.triple.point.exception.customException.ConflictException;
import com.triple.point.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewPointService {

    /*
    정책이 바뀔수도 있으니 db로 하는것이 나을지 고민
     */
    // 컨텐츠 존재하면 부여하는 포인트
    private final int EXIST_CONTENT_POINT = 1;
    // 사진이 존재하면 부여하는 포인트
    private final int EXIST_ATTACHED_PHOTO_POINT = 1;
    // 리뷰 처음 작성하면 부여하는 포인트
    private final int EXIST_FIRST_REVIEW_POINT = 1;

    private final PointRepository pointRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewHistoryRepository reviewHistoryRepository;

    @Transactional
    public PointType managePoint(ReviewEventRequest request) {
        if (request.isAdd()) {
            return addReview(request);
        }
        if (request.isMod()) {
            return modReview(request);
        }
        if (request.isDelete()) {
            return deleteReview(request);
        }
        throw new NotFoundException("존재하지 않는 포인트 로직입니다.");
    }

    /*
     * 한 사용자는 장소마다 리뷰를 1개만 작성할 수 있는 validation 체크는
     * 이벤트 발생전 리뷰작성이 이뤄질 때 체크하는게 맞는것 같지만 혹시 모르니까 체크
     */
    private PointType addReview(ReviewEventRequest request) {
        reviewRepository.findByUserIdAndPlaceId(request.getUserId(), request.getPlaceId())
                .ifPresent(review -> {
                    throw new ConflictException("한 사람당 한 장소의 리뷰는 하나입니다.");
                });
        reviewRepository.save(request.toReviewEntity());
        int contentPoint = request.existContent(EXIST_CONTENT_POINT);
        int attachedPhotoPoint = request.existAttachedPhoto(EXIST_ATTACHED_PHOTO_POINT);
        int firstReviewPoint = ReviewPointServiceUtils.validateFirstReview(reviewRepository, request.getPlaceId(), EXIST_FIRST_REVIEW_POINT);

        Point userPoint = pointRepository.findByUserId(request.getUserId())
                .orElseGet(() -> pointRepository.save(Point.of(request.getUserId())));
        int point = contentPoint + attachedPhotoPoint + firstReviewPoint;
        userPoint.addPoint(point);

        return PointType.of(EXIST_CONTENT_POINT, contentPoint, EXIST_ATTACHED_PHOTO_POINT, attachedPhotoPoint, EXIST_FIRST_REVIEW_POINT, firstReviewPoint);
    }

    private PointType modReview(ReviewEventRequest request) {
        ReviewHistory reviewHistory = ReviewPointServiceUtils.findNotDeleteReviewHistory(reviewHistoryRepository, request.getUserId(), request.getPlaceId());

        int contentPoint = reviewHistory.calculateContentPoint(request, EXIST_CONTENT_POINT);
        int attachedPhotoPoint = reviewHistory.calculateAttachedPhotoPoint(request, EXIST_ATTACHED_PHOTO_POINT);
        int firstReviewPoint = reviewHistory.calculateFirstReviewPoint();

        Point userPoint = pointRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new NotFoundException("존재하는 포인트가 없습니다."));
        int point = contentPoint + attachedPhotoPoint + firstReviewPoint;
        userPoint.updatePoint(point);

        return PointType.of(EXIST_CONTENT_POINT, contentPoint, EXIST_ATTACHED_PHOTO_POINT, attachedPhotoPoint, EXIST_FIRST_REVIEW_POINT, firstReviewPoint);
    }

    private PointType deleteReview(ReviewEventRequest request) {
        Review review = reviewRepository.findReviewById(request.getReviewId())
                .orElseThrow(() -> new NotFoundException(String.format("존재하는 리뷰 (%s) 가 없어 delete 하지 못합니다.", request.getReviewId())));
        review.updateDelete();
        ReviewHistory reviewHistory = ReviewPointServiceUtils.findNotDeleteReviewHistory(reviewHistoryRepository, request.getUserId(), request.getPlaceId());
        Point userPoint = pointRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new NotFoundException("존재하는 포인트가 없습니다."));
        int point = ReviewPointServiceUtils.calculateDeletePoint(reviewHistory.getPointType());
        userPoint.deletePoint(point);
        return PointType.of(EXIST_CONTENT_POINT, -reviewHistory.getPointType().getContentPoint(),
                EXIST_ATTACHED_PHOTO_POINT, -reviewHistory.getPointType().getAttachedPhotoPoint(),
                EXIST_FIRST_REVIEW_POINT, -reviewHistory.getPointType().getFirstReviewPoint());
    }

}
