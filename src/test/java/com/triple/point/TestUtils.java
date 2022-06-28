package com.triple.point;

import com.triple.point.domain.history.Action;
import com.triple.point.domain.history.HistoryImage;
import com.triple.point.domain.history.PointType;
import com.triple.point.domain.history.ReviewHistory;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

    public static void assertPointType(PointType pointType, int existContentPoint, int contentPoint, boolean isContentPoint,
                                       int existAttachedPhotoPoint, int attachedPhotoPoint, boolean isAttachedPhotoPoint,
                                       int existFirstReviewPoint, int firstReviewPoint, boolean isFirstReviewPoint) {
        assertThat(pointType.getExistContentPoint()).isEqualTo(existContentPoint);
        assertThat(pointType.getContentPoint()).isEqualTo(contentPoint);
        assertThat(pointType.getIsContentPoint()).isEqualTo(isContentPoint);

        assertThat(pointType.getExistAttachedPhotoPoint()).isEqualTo(existAttachedPhotoPoint);
        assertThat(pointType.getAttachedPhotoPoint()).isEqualTo(attachedPhotoPoint);
        assertThat(pointType.getIsAttachedPhotoPoint()).isEqualTo(isAttachedPhotoPoint);

        assertThat(pointType.getExistFirstReviewPoint()).isEqualTo(existFirstReviewPoint);
        assertThat(pointType.getFirstReviewPoint()).isEqualTo(firstReviewPoint);
        assertThat(pointType.getIsFirstReviewPoint()).isEqualTo(isFirstReviewPoint);
    }

    public static void assertReviewHistory(ReviewHistory reviewHistory, Action action, String reviewId, String userId, String placeId) {
        assertThat(reviewHistory.getAction()).isEqualTo(action);
        assertThat(reviewHistory.getReviewId()).isEqualTo(reviewId);
        assertThat(reviewHistory.getUserId()).isEqualTo(userId);
        assertThat(reviewHistory.getPlaceId()).isEqualTo(placeId);
    }

    public static void assertHistoryImage(HistoryImage historyImage1, HistoryImage historyImage2, String requestHistoryImage1, String requestHistoryImage2) {
        assertThat(historyImage1.getAttachedPhotoId()).isEqualTo(requestHistoryImage1);
        assertThat(historyImage2.getAttachedPhotoId()).isEqualTo(requestHistoryImage2);
    }

}
