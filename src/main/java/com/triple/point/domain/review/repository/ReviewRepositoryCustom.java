package com.triple.point.domain.review.repository;

import com.triple.point.domain.review.Review;

import java.util.Optional;

public interface ReviewRepositoryCustom {

    Review existsByPlaceId(String placeId);

    Optional<Review> findReviewById(String reviewId);

    Optional<Review> findByUserIdAndPlaceId(String userId, String placeId);

}
