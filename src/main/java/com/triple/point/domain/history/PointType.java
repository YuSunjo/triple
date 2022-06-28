package com.triple.point.domain.history;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PointType {

    // 컨텐츠 존재하면 부여하는 포인트
    private int existContentPoint;

    // 컨텐츠로 받은 포인트
    private int contentPoint;

    // 컨텐츠로 포인트를 받았는지 여부
    private Boolean isContentPoint;

    // 사진이 존재하면 부여하는 포인트
    private int existAttachedPhotoPoint;

    // 사진 실제 받은 포인트
    private int attachedPhotoPoint;

    // 사진으로 포인트를 받았는지 여부
    private Boolean isAttachedPhotoPoint;

    // 리뷰 처음 작성하면 부여하는 포인트
    private int existFirstReviewPoint;

    // 처음 리뷰 실제 받은 포인트
    private int firstReviewPoint;

    // 처음 리뷰로 포인트를 받았는지 여부
    private Boolean isFirstReviewPoint;

    public PointType(int existContentPoint, int contentPoint, boolean isContentPoint,
                     int existAttachedPhotoPoint, int attachedPhotoPoint, boolean isAttachedPhotoPoint,
                     int existFirstReviewPoint, int firstReviewPoint, boolean isFirstReviewPoint) {
        this.existContentPoint = existContentPoint;
        this.contentPoint = contentPoint;
        this.isContentPoint = isContentPoint;
        this.existAttachedPhotoPoint = existAttachedPhotoPoint;
        this.attachedPhotoPoint = attachedPhotoPoint;
        this.isAttachedPhotoPoint = isAttachedPhotoPoint;
        this.existFirstReviewPoint = existFirstReviewPoint;
        this.firstReviewPoint = firstReviewPoint;
        this.isFirstReviewPoint = isFirstReviewPoint;
    }

    public static PointType of(int existContentPoint, int contentPoint, boolean isContentPoint,
                               int existAttachedPhotoPoint, int attachedPhotoPoint, boolean isAttachedPhotoPoint,
                               int existFirstReviewPoint, int firstReviewPoint, boolean isFirstReviewPoint) {
        return new PointType(existContentPoint, contentPoint, isContentPoint, existAttachedPhotoPoint, attachedPhotoPoint, isAttachedPhotoPoint, existFirstReviewPoint, firstReviewPoint, isFirstReviewPoint);
    }

}
