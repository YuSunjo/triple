package com.triple.point.service.event;

import com.triple.point.domain.history.PointType;
import com.triple.point.dto.event.ReviewEventRequest;
import com.triple.point.service.event.factory.EventFactoryFacade;
import com.triple.point.service.history.ReviewHistoryService;
import com.triple.point.service.point.ReviewPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewEventFacade implements EventFactoryFacade {

    private final ReviewHistoryService reviewHistoryService;
    private final ReviewPointService reviewPointService;

    @Override
    @Transactional
    public void handlingEvent(ReviewEventRequest request) {
        PointType pointType = reviewPointService.managePoint(request);
        reviewHistoryService.createReviewHistory(request, pointType);
    }

}
