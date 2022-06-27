package com.triple.point.service.history;

import com.triple.point.domain.history.PointType;
import com.triple.point.domain.history.ReviewHistory;
import com.triple.point.domain.history.repository.ReviewHistoryRepository;
import com.triple.point.dto.event.ReviewEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewHistoryService {

    private final ReviewHistoryRepository reviewHistoryRepository;

    @Transactional
    public void createReviewHistory(ReviewEventRequest request, PointType pointType) {
        ReviewHistory reviewHistory = reviewHistoryRepository.save(request.toEntity(pointType));
        reviewHistory.addHistoryImage(request.getAttachedPhotoIds());
    }

}
