package com.triple.point.service.event.factory;

import com.triple.point.domain.history.EventType;
import com.triple.point.exception.customException.NotFoundException;
import com.triple.point.service.event.ReviewEventFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventFactory {

    private final ReviewEventFacade reviewEventFacade;
    private final Map<EventType, EventFactoryFacade> eventFacadeMap = new EnumMap<>(EventType.class);

    @PostConstruct
    void eventMap() {
        eventFacadeMap.put(EventType.REVIEW, reviewEventFacade);
    }

    public EventFactoryFacade getEventFacade(EventType type) {
        EventFactoryFacade eventFacade = eventFacadeMap.get(type);
        if (eventFacade == null) {
            throw new NotFoundException(String.format("존재하지 않는 (%s) 서비스 입니다.", type));
        }
        return eventFacade;
    }

}
