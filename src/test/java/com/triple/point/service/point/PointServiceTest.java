package com.triple.point.service.point;

import com.triple.point.domain.point.Point;
import com.triple.point.domain.point.repository.PointRepository;
import com.triple.point.dto.point.PointInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PointServiceTest {

    @Autowired
    private PointService pointService;

    @Autowired
    private PointRepository pointRepository;

    @AfterEach
    void cleanUp() {
        pointRepository.deleteAll();
    }

    @DisplayName("유저가 포인트를 3포인트 가지고 있다.")
    @Test
    void getUserPoint_1() {
        // given
        String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
        Point point = Point.testInstance(userId, 3);
        pointRepository.save(point);

        // when
        PointInfoResponse userPoint = pointService.getUserPoint(userId);

        // then
        assertThat(userPoint.getPoint()).isEqualTo(3);
    }

    @DisplayName("유저가 포인트를 0포인트 가지고 있다.")
    @Test
    void getUserPoint_2() {
        // given
        String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
        Point point = Point.testInstance(userId, 0);
        pointRepository.save(point);

        // when
        PointInfoResponse userPoint = pointService.getUserPoint(userId);

        // then
        assertThat(userPoint.getPoint()).isEqualTo(0);
    }

    @DisplayName("유저가 포인트를 -3포인트 가지고 있다.")
    @Test
    void getUserPoint_3() {
        // given
        String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
        Point point = Point.testInstance(userId, -3);
        pointRepository.save(point);

        // when
        PointInfoResponse userPoint = pointService.getUserPoint(userId);

        // then
        assertThat(userPoint.getPoint()).isEqualTo(-3);
    }

}
