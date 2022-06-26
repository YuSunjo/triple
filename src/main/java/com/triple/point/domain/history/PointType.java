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

    // 컨텐츠 실제 받은 포인트
    private int contentPoint;

    // 사진이 존재하면 부여하는 포인트
    private int existAttachedPhotoPoint;

    // 사진 실제 받은 포인트
    private int attachedPhotoPoint;

    // 리뷰 처음 작성하면 부여하는 포인트
    private int existFirstReviewPoint;

    // 처음 리뷰 실제 받은 포인트
    private int firstReviewPoint;

    public PointType(int existContentPoint, int contentPoint, int existAttachedPhotoPoint, int attachedPhotoPoint, int existFirstReviewPoint, int firstReviewPoint) {
        this.existContentPoint = existContentPoint;
        this.contentPoint = contentPoint;
        this.existAttachedPhotoPoint = existAttachedPhotoPoint;
        this.attachedPhotoPoint = attachedPhotoPoint;
        this.existFirstReviewPoint = existFirstReviewPoint;
        this.firstReviewPoint = firstReviewPoint;
    }

    public static PointType of(int existContentPoint, int contentPoint, int existAttachedPhotoPoint, int attachedPhotoPoint, int existFirstReviewPoint, int firstReviewPoint) {
        return new PointType(existContentPoint, contentPoint, existAttachedPhotoPoint, attachedPhotoPoint, existFirstReviewPoint, firstReviewPoint);
    }

}
