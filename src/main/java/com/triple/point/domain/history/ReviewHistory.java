package com.triple.point.domain.history;

import com.triple.point.domain.BaseTimeEntity;
import com.triple.point.dto.event.ReviewEventRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        indexes = {
                @Index(name = "review_history_review", columnList = "reviewId"),
                @Index(name = "review_history_user", columnList = "userId"),
                @Index(name = "review_history_place", columnList = "placeId")
        }
)
public class ReviewHistory extends BaseTimeEntity {

    @Id
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(nullable = false)
    private String reviewId;

    private String content;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String placeId;

    @Embedded
    private PointType pointType;

    @OneToMany(mappedBy = "reviewHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<HistoryImage> historyImageList = new ArrayList<>();

    @Builder
    public ReviewHistory(Action action, String reviewId, String content, String userId, String placeId, PointType pointType) {
        this.id = UUID.randomUUID().toString();
        this.action = action;
        this.reviewId = reviewId;
        this.content = content;
        this.userId = userId;
        this.placeId = placeId;
        this.pointType = pointType;
    }

    public void addHistoryImage(List<String> attachedPhotoIds) {
        List<HistoryImage> historyImageList = attachedPhotoIds.stream().map(attachedPhotoId -> HistoryImage.of(this, attachedPhotoId))
                .collect(Collectors.toList());
        this.historyImageList.addAll(historyImageList);
    }

    public boolean isDelete() {
        return this.action.equals(Action.DELETE);
    }


    private boolean isContentPoint() {
        return this.pointType.getContentPoint() > 0;
    }

    private boolean isAttachedPhotoPoint() {
        return this.pointType.getAttachedPhotoPoint() > 0;
    }

    public int calculateContentPoint(ReviewEventRequest request, int existContentPoint) {
        // 과거 컨텐츠 포인트가 없었다면?
        if ((!this.isContentPoint())) {
            return request.existContent(existContentPoint);
        }
        // request - 컨텐츠 길이가 1이상이였다가 컨텐츠 길이가 0 이면 마이너스 해줘야함
        if (!request.isContent()) {
            return -(this.pointType.getContentPoint());
        }
        return 0;
    }

    public int calculateAttachedPhotoPoint(ReviewEventRequest request, int existAttachedPhotoPoint) {
        // 과거 사진 포인트가 없었다면?
        if (!this.isAttachedPhotoPoint()) {
            return request.existAttachedPhoto(existAttachedPhotoPoint);
        }
        if (!request.isAttachedPhoto()) {
            return -(this.pointType.getAttachedPhotoPoint());
        }
        return 0;
    }

    public int calculateFirstReviewPoint() {
        return 0;
    }

}
