package com.triple.point.domain.history.repository;

import com.triple.point.domain.history.ReviewHistory;

import java.util.Optional;

public interface ReviewHistoryRepositoryCustom {

    Optional<ReviewHistory> findByUserIdAndPlaceId(String userId, String placeId);

}
