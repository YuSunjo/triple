package com.triple.point.service.event;

import com.triple.point.dto.event.ReviewEventRequest;
import com.triple.point.service.event.factory.EventFactoryFacade;
import com.triple.point.service.history.ReviewHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewEventFacade implements EventFactoryFacade {

    private final ReviewHistoryService reviewHistoryService;

    @Override
    @Transactional
    public void collectPoint(ReviewEventRequest request) {
        reviewHistoryService.createReviewHistory(request);
    }

}
