package com.triple.point.service.point;

import com.triple.point.TestUtils;
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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewPointServiceTest {

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

    @DisplayName("리뷰 작성 ADD - 컨텐츠 1자 이상 사진 1장 이상 리뷰 처음 썼을 경우")
    @Test
    void reviewPoint_1() {
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

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(3);
        TestUtils.assertPointType(pointType, 1, 1, 1, 1, 1, 1);
    }

    @DisplayName("리뷰 작성 ADD - 컨텐츠 1자 이상 사진 1장 이상 리뷰 처음 썼을 경우가 아닌 경우")
    @Test
    void reviewPoint_2() {
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

        // 이미 리뷰를 쓴 사람이 있다.
        Review review = Review.testInstance("2e4baf1c-5acb-4efb-a1af-eddada31b00f", "userId", "좋아요!");
        reviewRepository.save(review);

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(2);
        TestUtils.assertPointType(pointType, 1, 1, 1, 1, 1, 0);
    }

    @DisplayName("리뷰 작성 ADD - 컨텐츠 1자 이상 사진 0장 리뷰 처음 썼을 경우가 아닌 경우")
    @Test
    void reviewPoint_3() {
        // given
        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // 이미 리뷰를 쓴 사람이 있다.
        Review review = Review.testInstance("2e4baf1c-5acb-4efb-a1af-eddada31b00f", "userId", "title", "좋아요!");
        reviewRepository.save(review);

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(1);
        TestUtils.assertPointType(pointType, 1, 1, 1, 0, 1, 0);
    }

    @DisplayName("리뷰 작성 MOD - 컨텐츠 1자 이상 사진 1장 리뷰 처음 썼을 경우 -> 컨텐츠 1자 이상 사진 0장 으로 변경했을 경우, MOD일 경우 처음 쓴 리뷰는 고려 안함")
    @Test
    void reviewPoint_4() {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!~~~~~~~~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, 1, 1, 1, 1))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 3);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(2);
        TestUtils.assertPointType(pointType, 1, 0, 1, -1, 1, 0);
    }

    @DisplayName("리뷰 작성 MOD - 컨텐츠 1자 이상 사진 1장 리뷰 처음 썼을 경우 -> 컨텐츠 0자 이상 사진 0장 으로 변경했을 경우, MOD일 경우 처음 쓴 리뷰는 고려 안함")
    @Test
    void reviewPoint_5() {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!~~~~~~~~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, 1, 1, 1, 1))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 3);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(1);
        TestUtils.assertPointType(pointType, 1, -1, 1, -1, 1, 0);
    }

    @DisplayName("리뷰 작성 MOD - 컨텐츠 0자 사진 1장 리뷰 처음 썼을 경우 -> 컨텐츠 1자 이상 사진 0장 으로 변경했을 경우, MOD일 경우 처음 쓴 리뷰는 고려 안함")
    @Test
    void reviewPoint_6() {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 0, 1, 1, 1, 1))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 2);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요~~")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(2);
        TestUtils.assertPointType(pointType, 1, 1, 1, -1, 1, 0);
    }

    @DisplayName("리뷰 작성 MOD - 과거에는 포인트를 2점씩 현재는 1점씩, 컨텐츠 0자 사진 0장 리뷰 처음 썼을 경우 -> 컨텐츠 1자 이상 사진 0장 으로 변경했을 경우, MOD일 경우 처음 쓴 리뷰는 고려 안함")
    @Test
    void reviewPoint_7() {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(2, 0, 2, 0, 2, 2))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 2);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요~~")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(3);
        TestUtils.assertPointType(pointType, 1, 1, 1, 0, 1, 0);
    }

    @DisplayName("리뷰 작성 MOD - 과거에는 포인트를 2점씩 현재는 1점씩, 컨텐츠 1자 사진 0장 리뷰 처음 썼을 경우 -> 컨텐츠 0자 이상 사진 0장 으로 변경했을 경우(과거 포인트로 깎임), MOD일 경우 처음 쓴 리뷰는 고려 안함")
    @Test
    void reviewPoint_8() {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(2, 2, 2, 0, 2, 2))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 4);
        pointRepository.save(point);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(2);
        TestUtils.assertPointType(pointType, 1, -2, 1, 0, 1, 0);
    }

    @DisplayName("리뷰 작성 DELETE - 컨텐츠 1자 사진 0장 리뷰 처음 썼을 경우 -> 포인트 모두 깎임")
    @Test
    void reviewPoint_9() {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.ADD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, 1, 0, 1, 1))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 2);
        pointRepository.save(point);

        Review review = Review.testInstance("240a0658-dc5f-4878-9381-ebb7b2667772", "2e4baf1c-5acb-4efb-a1af-eddada31b00f", "userId", "content");
        reviewRepository.save(review);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.DELETE)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("")
                .attachedPhotoIds(Collections.emptyList())
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(0);
        TestUtils.assertPointType(pointType, 1, -1, 1, 0, 1, -1);
    }

    @DisplayName("리뷰 작성 MOD - 컨텐츠 0자 사진 1장 리뷰 처음 썼을 경우 -> 컨텐츠 1자 이상 사진 0장 -> 컨텐츠 1자 이상 사진 1장 이상, MOD일 경우 처음 쓴 리뷰는 고려 안함 ")
    @Test
    void reviewPoint_10() {
        // given
        ReviewHistory reviewHistory = ReviewHistory.builder()
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요~~")
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .pointType(PointType.of(1, 1, 1, -1, 1, 0))
                .build();
        reviewHistoryRepository.save(reviewHistory);

        Point point = Point.testInstance("3ede0ef2-92b7-4817-a5f3-0c575361f745", 2);
        pointRepository.save(point);

        Review review = Review.testInstance("240a0658-dc5f-4878-9381-ebb7b2667772", "2e4baf1c-5acb-4efb-a1af-eddada31b00f", "userId", "content");
        reviewRepository.save(review);

        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.MOD)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아용~~~~")
                .attachedPhotoIds(List.of("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        // when
        PointType pointType = reviewPointService.managePoint(request);

        // then
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList.size()).isEqualTo(1);
        assertThat(pointList.get(0).getUserId()).isEqualTo(request.getUserId());
        assertThat(pointList.get(0).getPoint()).isEqualTo(3);
        TestUtils.assertPointType(pointType, 1, 0, 1, 1, 1, 0);
    }

}
