package com.triple.point.dto.event;

import com.triple.point.domain.history.Action;
import com.triple.point.domain.history.EventType;
import com.triple.point.domain.history.PointType;
import com.triple.point.domain.history.ReviewHistory;
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
