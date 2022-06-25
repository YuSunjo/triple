package com.triple.point.domain.history;

import com.triple.point.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        indexes = {
                @Index(name = "history_image_review_history_index", columnList = "review_history_id")
        }
)
public class HistoryImage extends BaseTimeEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_history_id", nullable = false, foreignKey = @ForeignKey(name="history_image_review_history_fk"))
    private ReviewHistory reviewHistory;

    @Column(nullable = false)
    private String attachedPhotoId;

    public HistoryImage(ReviewHistory reviewHistory, String attachedPhotoId) {
        this.id = UUID.randomUUID().toString();
        this.reviewHistory = reviewHistory;
        this.attachedPhotoId = attachedPhotoId;
    }

    public static HistoryImage of(ReviewHistory reviewHistory, String attachedPhotoId) {
        return new HistoryImage(reviewHistory, attachedPhotoId);
    }

}
