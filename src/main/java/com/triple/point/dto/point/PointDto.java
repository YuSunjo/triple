package com.triple.point.dto.point;

import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: 2022/06/28 이름 고민좀~
@Getter
@NoArgsConstructor
public class PointDto {

    private int point;
    private Boolean isPoint;

    public PointDto(int point, boolean isPoint) {
        this.point = point;
        this.isPoint = isPoint;
    }

    public static PointDto of(int point, boolean isPoint) {
        return new PointDto(point, isPoint);
    }

}
