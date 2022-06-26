package com.triple.point.domain.point.repository;

import com.triple.point.domain.point.Point;

import java.util.Optional;

public interface PointRepositoryCustom {

    Optional<Point> findByUserId(String userId);

}
