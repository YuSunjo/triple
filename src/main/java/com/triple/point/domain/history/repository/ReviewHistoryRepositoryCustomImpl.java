package com.triple.point.domain.history.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triple.point.domain.history.QReviewHistory;
import com.triple.point.domain.history.ReviewHistory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.triple.point.domain.history.QReviewHistory.*;

@RequiredArgsConstructor
public class ReviewHistoryRepositoryCustomImpl implements ReviewHistoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ReviewHistory> findByUserIdAndPlaceId(String userId, String placeId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(reviewHistory)
                        .where(
                                reviewHistory.userId.eq(userId),
                                reviewHistory.placeId.eq(placeId)
                        )
                        .orderBy(reviewHistory.id.desc())
                        .fetchFirst()
        );
    }

}
