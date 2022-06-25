package com.triple.point.controller.event;

import com.triple.point.ApiResponse;
import com.triple.point.dto.event.ReviewEventRequest;
import com.triple.point.service.event.factory.EventFactory;
import com.triple.point.service.event.factory.EventFactoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventFactory eventFactory;

    @PostMapping("/events")
    public ApiResponse<String> reviewEvent(@RequestBody @Valid ReviewEventRequest request) {
        EventFactoryFacade eventFacade = eventFactory.getEventFacade(request.getType());
        eventFacade.collectPoint(request);
        return ApiResponse.OK;
    }

}
