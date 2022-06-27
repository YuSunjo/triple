package com.triple.point.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triple.point.domain.review.Review;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.triple.point.domain.review.QReview.*;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Review existsByPlaceId(String placeId) {
        return queryFactory.selectFrom(review)
                .where(
                        review.placeId.eq(placeId),
                        review.deleteDate.isNull()
                )
                .fetchFirst();
    }

    @Override
    public Optional<Review> findReviewById(String reviewId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(review)
                        .where(
                                review.id.eq(reviewId),
                                review.deleteDate.isNull()
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<Review> findByUserIdAndPlaceId(String userId, String placeId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(review)
                        .where(
                                review.userId.eq(userId),
                                review.placeId.eq(placeId),
                                review.deleteDate.isNull()
                        )
                        .fetchOne()
        );
    }

}
