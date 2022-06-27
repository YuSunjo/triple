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

    private int point;

    public Point(String userId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.point = 0;
    }

    // testInstance 에서만 사용 가능
    private Point(String userId, int point) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.point = point;
    }

    public static Point of(String userId) {
        return new Point(userId);
    }

    public static Point testInstance(String userId, int point) {
        return new Point(userId, point);
    }

    public void addPoint(int point) {
        this.point += point;
    }

    public void deletePoint(int point) {
        this.point += point;
    }

    public void updatePoint(int point) {
        this.point += point;
    }

}
