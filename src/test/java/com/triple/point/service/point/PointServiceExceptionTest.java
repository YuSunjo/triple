package com.triple.point.service.point;

import com.triple.point.exception.customException.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class PointServiceExceptionTest {

    @Autowired
    private PointService pointService;

    @DisplayName("유저의 포인트가 없을 경우 NotFoundException 발생")
    @Test
    void getUserPoint_1() {
        // given
        String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";

        // when & then
        assertThatThrownBy(
                () -> pointService.getUserPoint(userId)
        ).isInstanceOf(NotFoundException.class);
    }

}
