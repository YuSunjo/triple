package com.triple.point.service.point;

import com.triple.point.domain.history.PointType;
import com.triple.point.domain.history.ReviewHistory;
import com.triple.point.domain.history.repository.ReviewHistoryRepository;
import com.triple.point.domain.review.Review;
import com.triple.point.domain.review.repository.ReviewRepository;
import com.triple.point.exception.customException.NotFoundException;
import com.triple.point.exception.customException.ValidationException;

public class ReviewPointServiceUtils {

    public static int validateReview(ReviewRepository reviewRepository, String placeId, int existFirstReviewPoint) {
        Review review = reviewRepository.existsByPlaceId(placeId);
        if (review != null) {
            return 0;
        }
        return existFirstReviewPoint;
    }

    public static int calculateDeletePoint(PointType pointType) {
        int contentPoint = pointType.getContentPoint();
        int attachedPhotoPoint = pointType.getAttachedPhotoPoint();
        int firstReviewPoint = pointType.getFirstReviewPoint();
        return -(contentPoint + attachedPhotoPoint + firstReviewPoint);
    }

    public static ReviewHistory findNotDeleteReviewHistory(ReviewHistoryRepository reviewHistoryRepository, String userId, String placeId) {
        ReviewHistory reviewHistory = reviewHistoryRepository.findByUserIdAndPlaceId(userId, placeId)
                .orElseThrow(() -> new NotFoundException("최신 history가 없어 포인트가 그대로 유지됩니다."));
        if (reviewHistory.isDelete()) {
            throw new ValidationException("최근에 delete 했을 경우 update를 할 수 없습니다.");
        }
        return reviewHistory;
    }

}
