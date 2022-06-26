package com.triple.point.domain.point.repository;

import com.triple.point.domain.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, String>, PointRepositoryCustom {
}
