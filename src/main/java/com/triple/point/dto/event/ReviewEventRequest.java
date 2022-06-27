package com.triple.point.dto.event;

import com.triple.point.domain.history.Action;
import com.triple.point.domain.history.EventType;
import com.triple.point.domain.history.PointType;
import com.triple.point.domain.history.ReviewHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewEventRequest {

    @NotNull
    private EventType type;

    @NotNull
    private Action action;

    @NotBlank
    private String reviewId;

    private String content;

    private List<String> attachedPhotoIds;

    @NotBlank
    private String userId;

    @NotBlank
    private String placeId;

    @Builder(builderMethodName = "testBuilder")
    public ReviewEventRequest(EventType type, Action action, String reviewId, String content, List<String> attachedPhotoIds, String userId, String placeId) {
        this.type = type;
        this.action = action;
        this.reviewId = reviewId;
        this.content = content;
        this.attachedPhotoIds = attachedPhotoIds;
        this.userId = userId;
        this.placeId = placeId;
    }

    public ReviewHistory toEntity(PointType pointType) {
        return ReviewHistory.builder()
                .action(action)
                .reviewId(reviewId)
                .content(content)
                .userId(userId)
                .placeId(placeId)
                .pointType(pointType)
                .build();
    }

    public boolean isAdd() {
        return action.equals(Action.ADD);
    }

    public boolean isMod() {
        return action.equals(Action.MOD);
    }

    public boolean isDelete() {
        return action.equals(Action.DELETE);
    }

    public boolean isContent() {
        return this.content.length() >= 1;
    }

    public boolean isAttachedPhoto() {
        return this.attachedPhotoIds.size() >= 1;
    }

    public int existContent(int existContentPoint) {
        if (this.content.length() >= 1) {
            return existContentPoint;
        }
        return 0;
    }

    public int existAttachedPhoto(int existAttachedPhotoPoint) {
        if (this.attachedPhotoIds.size() >= 1) {
            return existAttachedPhotoPoint;
        }
        return 0;
    }

}
