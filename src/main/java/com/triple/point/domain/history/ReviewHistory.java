package com.triple.point.domain.history;

import com.triple.point.domain.BaseTimeEntity;
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
public class ReviewHistory extends BaseTimeEntity {

    @Id
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;

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

    @OneToMany(mappedBy = "reviewHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<HistoryImage> historyImageList = new ArrayList<>();

    @Builder
    public ReviewHistory(EventType type, Action action, String reviewId, String content, String userId, String placeId) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.action = action;
        this.reviewId = reviewId;
        this.content = content;
        this.userId = userId;
        this.placeId = placeId;
    }

    public void addHistoryImage(List<String> attachedPhotoIds) {
        List<HistoryImage> historyImageList = attachedPhotoIds.stream().map(attachedPhotoId -> HistoryImage.of(this, attachedPhotoId))
                .collect(Collectors.toList());
        this.historyImageList.addAll(historyImageList);
    }

}
