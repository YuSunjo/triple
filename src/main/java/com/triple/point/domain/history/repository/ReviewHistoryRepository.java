package com.triple.point.domain.history.repository;

import com.triple.point.domain.history.ReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewHistoryRepository extends JpaRepository<ReviewHistory, String> {
}
