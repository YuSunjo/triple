package com.triple.point.domain.review.repository;

import com.triple.point.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
