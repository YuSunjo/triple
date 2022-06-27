package com.triple.point.service.event.factory;

import com.triple.point.dto.event.ReviewEventRequest;

public interface EventFactoryFacade {

    void handlingEvent(ReviewEventRequest request);

}
