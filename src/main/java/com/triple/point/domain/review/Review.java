package com.triple.point.domain.review;

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
                @Index(name = "review_place_index", columnList = "placeId")
        }
)
public class Review extends BaseTimeEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String placeId;

    @Column(nullable = false)
    private String title;

    private String content;

    public Review(String placeId, String title, String content) {
        this.id = UUID.randomUUID().toString();
        this.placeId = placeId;
        this.title = title;
        this.content = content;
    }

}
