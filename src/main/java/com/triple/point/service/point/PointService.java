package com.triple.point.service.point;

import com.triple.point.domain.point.Point;
import com.triple.point.domain.point.repository.PointRepository;
import com.triple.point.dto.point.PointInfoResponse;
import com.triple.point.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    @Transactional(readOnly = true)
    public PointInfoResponse getUserPoint(String userId) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(String.format("(%s)는 포인트가 존재하지 않습니다.", userId)));
        return PointInfoResponse.of(point);
    }

}
