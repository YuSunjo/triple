package com.triple.point.controller.point;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.point.domain.history.Action;
import com.triple.point.domain.history.EventType;
import com.triple.point.domain.history.repository.ReviewHistoryRepository;
import com.triple.point.domain.point.Point;
import com.triple.point.domain.point.repository.PointRepository;
import com.triple.point.domain.review.repository.ReviewRepository;
import com.triple.point.dto.event.ReviewEventRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewHistoryRepository reviewHistoryRepository;

    @Autowired
    private PointRepository pointRepository;

    @AfterEach
    void cleanUp() {
        reviewRepository.deleteAll();
        reviewHistoryRepository.deleteAll();
        pointRepository.deleteAll();
    }

    @DisplayName("포인트를 조회한다.")
    @Test
    void getUserPoint_1() throws Exception {
        // given
        String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
        Point point = Point.testInstance(userId, 3);
        pointRepository.save(point);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/user/point")
                        .header(HttpHeaders.AUTHORIZATION, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.point").value(3))
                .andExpect(jsonPath("$.result").value("00000"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

}
