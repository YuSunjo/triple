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
        /*
         * 한 사용자는 장소마다 리뷰를 1개만 작성할 수 있는 validation 체크는
         * 이벤트 발생전 리뷰작성이 이뤄질 때 체크.
         */
        PointType pointType = reviewPointService.managePoint(request);
        reviewHistoryService.createReviewHistory(request, pointType);
    }

}
