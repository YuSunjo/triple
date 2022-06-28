package com.triple.point.dto.point;

import com.triple.point.domain.point.Point;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointInfoResponse {

    private String id;
    private String userId;
    private Integer point;

    public PointInfoResponse(String id, String userId, Integer point) {
        this.id = id;
        this.userId = userId;
        this.point = point;
    }

    public static PointInfoResponse of(Point point) {
        return new PointInfoResponse(point.getId(), point.getUserId(), point.getPoint());
    }

}
