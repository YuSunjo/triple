package com.triple.point.controller.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.point.domain.history.Action;
import com.triple.point.domain.history.EventType;
import com.triple.point.domain.history.PointType;
import com.triple.point.domain.history.ReviewHistory;
import com.triple.point.domain.history.repository.ReviewHistoryRepository;
import com.triple.point.domain.point.Point;
import com.triple.point.domain.point.repository.PointRepository;
import com.triple.point.domain.review.Review;
import com.triple.point.domain.review.repository.ReviewRepository;
import com.triple.point.dto.event.ReviewEventRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

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

    @DisplayName("리뷰 작성하면 이벤트 발생 - 처음 ADD하면 200처리가 된다.")
    @Test
    void handlingEvent_1() throws Exception {
        // given
        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(""))
                .andExpect(jsonPath("$.result").value("00000"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("리뷰 작성하면 이벤트 발생 - ADD 한 후에 MOD하면 200처리가 된다.")
    @Test
    void handlingEvent_2() throws Exception {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!~~~~~~~~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, true, 1, 1, true, 1, 1, true))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 3);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(""))
                .andExpect(jsonPath("$.result").value("00000"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("리뷰 작성하면 이벤트 발생 - MOD 한 후에 MOD하면 200처리가 된다.")
    @Test
    void handlingEvent_3() throws Exception {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!~~~~~~~~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, true, 1, 1, true, 1, 1, true))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 3);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(""))
                .andExpect(jsonPath("$.result").value("00000"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("리뷰 작성하면 이벤트 발생 - MOD 한 후에 DELETE하면 200처리가 된다.")
    @Test
    void handlingEvent_4() throws Exception {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!~~~~~~~~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, true, 1, 1, true, 1, 1, true))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Review review = Review.testInstance("240a0658-dc5f-4878-9381-ebb7b2667772", "2e4baf1c-5acb-4efb-a1af-eddada31b00f", "userId", "content");
        reviewRepository.save(review);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 3);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.DELETE)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(""))
                .andExpect(jsonPath("$.result").value("00000"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("리뷰 작성하면 이벤트 발생 - ADD 한 후에 DELETE하면 200처리가 된다.")
    @Test
    void handlingEvent_5() throws Exception {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!~~~~~~~~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, true, 1, 1, true, 1, 1, true))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Review review = Review.testInstance("240a0658-dc5f-4878-9381-ebb7b2667772", "2e4baf1c-5acb-4efb-a1af-eddada31b00f", "userId", "content");
        reviewRepository.save(review);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 3);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.DELETE)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/events")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(""))
                .andExpect(jsonPath("$.result").value("00000"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

}
