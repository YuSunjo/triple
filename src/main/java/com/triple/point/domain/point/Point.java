package com.triple.point.domain.point;

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
                @Index(name = "point_user_index", columnList = "userId")
        }
)
public class Point extends BaseTimeEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String userId;

    public Point(String userId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
    }

}
