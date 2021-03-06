package com.triple.point.service.point;

import com.triple.point.domain.history.ReviewHistory;
import com.triple.point.domain.history.repository.ReviewHistoryRepository;
import com.triple.point.domain.review.Review;
import com.triple.point.domain.review.repository.ReviewRepository;
import com.triple.point.dto.event.ReviewEventRequest;
import com.triple.point.dto.point.PointDto;
import com.triple.point.exception.customException.ConflictException;
import com.triple.point.exception.customException.NotFoundException;
import com.triple.point.exception.customException.ValidationException;

public class ReviewPointServiceUtils {

    public static PointDto validateFirstReview(ReviewRepository reviewRepository, String placeId, int existFirstReviewPoint) {
        Review review = reviewRepository.existsByPlaceId(placeId);
        if (review != null) {
            return PointDto.of(0, false);
        }
        return PointDto.of(existFirstReviewPoint, true);
    }

    public static ReviewHistory findNotDeleteReviewHistory(ReviewHistoryRepository reviewHistoryRepository, String userId, String placeId) {
        ReviewHistory reviewHistory = reviewHistoryRepository.findByUserIdAndPlaceId(userId, placeId)
                .orElseThrow(() -> new NotFoundException("최신 history가 없어 포인트가 그대로 유지됩니다."));
        if (reviewHistory.isDelete()) {
            throw new ValidationException("최근에 delete 했을 경우 이벤트 발생 할 수 없습니다.");
        }
        return reviewHistory;
    }

    public static void validateReview(ReviewRepository reviewRepository, ReviewEventRequest request) {
        reviewRepository.findByUserIdAndPlaceId(request.getUserId(), request.getPlaceId())
                .ifPresent(review -> {
                    throw new ConflictException("한 사람당 한 장소의 리뷰는 하나입니다.");
                });
        reviewRepository.save(request.toReviewEntity());
    }

}
