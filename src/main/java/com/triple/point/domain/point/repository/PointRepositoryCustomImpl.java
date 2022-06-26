package com.triple.point.domain.point.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triple.point.domain.point.Point;
import com.triple.point.domain.point.QPoint;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.triple.point.domain.point.QPoint.*;

@RequiredArgsConstructor
public class PointRepositoryCustomImpl implements PointRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Point> findByUserId(String userId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(point1)
                        .where(
                                point1.userId.eq(userId)
                        )
                        .fetchOne()
        );
    }

}
