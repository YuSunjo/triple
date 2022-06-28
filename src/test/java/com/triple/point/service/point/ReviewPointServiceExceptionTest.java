package com.triple.point.service.point;

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
import com.triple.point.exception.customException.ConflictException;
import com.triple.point.exception.customException.NotFoundException;
import com.triple.point.exception.customException.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ReviewPointServiceExceptionTest {

    @Autowired
    private ReviewPointService reviewPointService;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewHistoryRepository reviewHistoryRepository;

    @AfterEach
    void cleanUp() {
        pointRepository.deleteAll();
        reviewRepository.deleteAll();
        reviewHistoryRepository.deleteAll();
    }

    @DisplayName("MOD 이벤트 발생시 - ADD 없이 MOD 하려고 할 경우 NotFoundException 발생")
    @Test
    void reviewPoint_exception_1() {
        // given
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
        assertThatThrownBy(
                () -> reviewPointService.managePoint(request)
        ).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("MOD 이벤트 발생시 - ADD 후에 MOD 하려고 하는데 point 가 없다면 NotFoundException 발생")
    @Test
    void reviewPoint_exception_2() {
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

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();
        // when & then
        assertThatThrownBy(
                () -> reviewPointService.managePoint(request)
        ).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("DELETE 이벤트 발생시 - 이전에 DELETE 했는데 또 DELETE 하려는 경우 ValidationException")
    @Test
    void reviewPoint_exception_3() {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.DELETE)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!~~~~~~~~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, true, 1, 1, true, 1, 1, true))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 0);
        pointRepository.save(point);

        Review review = Review.testInstance("240a0658-dc5f-4878-9381-ebb7b2667772", "2e4baf1c-5acb-4efb-a1af-eddada31b00f", "userId", "content");
        reviewRepository.save(review);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.DELETE)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();
        // when & then
        assertThatThrownBy(
                () -> reviewPointService.managePoint(request)
        ).isInstanceOf(ValidationException.class);
    }

    @DisplayName("DELETE 이벤트 발생시 기존 리뷰가 없다면 NotFoundException")
    @Test
    void reviewPoint_exception_4() {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.DELETE)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!~~~~~~~~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, true, 1, 1, true, 1, 1, true))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 0);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.DELETE)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();
        // when & then
        assertThatThrownBy(
                () -> reviewPointService.managePoint(request)
        ).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("이미 같은 유저가 같은 장소에 리뷰를 했는데 또 할경우 ConflictException")
    @Test
    void reviewPoint_exception_5() {
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

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        reviewRepository.save(Review.testInstance(request.getReviewId(), request.getPlaceId(), request.getUserId(), request.getContent()));

        // when & then
        assertThatThrownBy(
                () -> reviewPointService.managePoint(request)
        ).isInstanceOf(ConflictException.class);
    }

}
