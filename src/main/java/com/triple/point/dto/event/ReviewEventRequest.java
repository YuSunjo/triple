package com.triple.point.dto.event;

import com.triple.point.domain.history.Action;
import com.triple.point.domain.history.EventType;
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

    public ReviewHistory toEntity() {
        return ReviewHistory.builder()
                .type(type)
                .action(action)
                .reviewId(reviewId)
                .content(content)
                .userId(userId)
                .placeId(placeId)
                .build();
    }

}
