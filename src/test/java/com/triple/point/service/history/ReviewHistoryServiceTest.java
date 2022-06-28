package com.triple.point.service.history;

import com.triple.point.TestUtils;
import com.triple.point.domain.history.*;
import com.triple.point.domain.history.repository.HistoryImageRepository;
import com.triple.point.domain.history.repository.ReviewHistoryRepository;
import com.triple.point.dto.event.ReviewEventRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReviewHistoryServiceTest {

    @Autowired
    private ReviewHistoryService reviewHistoryService;

    @Autowired
    private ReviewHistoryRepository reviewHistoryRepository;

    @Autowired
    private HistoryImageRepository historyImageRepository;

    @AfterEach
    void cleanUp() {
        reviewHistoryRepository.deleteAll();
    }

    @DisplayName("ADD 이벤트 발생시 reviewHistory 저장")
    @Test
    void reviewHistory_1() {
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

        PointType pointType = PointType.of(1, 1, true, 1, 1, true, 1, 1, true);

        // when
        reviewHistoryService.createReviewHistory(request, pointType);

        // then
        List<ReviewHistory> reviewHistoryList = reviewHistoryRepository.findAll();
        List<HistoryImage> historyImageList = historyImageRepository.findAll();
        assertThat(reviewHistoryList).hasSize(1);
        assertThat(historyImageList).hasSize(2);
        TestUtils.assertReviewHistory(reviewHistoryList.get(0), request.getAction(), request.getReviewId(), request.getUserId(), request.getPlaceId());
        TestUtils.assertHistoryImage(historyImageList.get(0), historyImageList.get(1), request.getAttachedPhotoIds().get(0), request.getAttachedPhotoIds().get(1));
    }

    @DisplayName("MOD 이벤트 발생시 reviewHistory 저장")
    @Test
    void reviewHistory_2() {
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

        PointType pointType = PointType.of(1, 1, true, 1, 1, true, 1, 1, true);

        // when
        reviewHistoryService.createReviewHistory(request, pointType);

        // then
        List<ReviewHistory> reviewHistoryList = reviewHistoryRepository.findAll();
        List<HistoryImage> historyImageList = historyImageRepository.findAll();
        assertThat(reviewHistoryList).hasSize(1);
        assertThat(historyImageList).hasSize(2);
        TestUtils.assertReviewHistory(reviewHistoryList.get(0), request.getAction(), request.getReviewId(), request.getUserId(), request.getPlaceId());
        TestUtils.assertHistoryImage(historyImageList.get(0), historyImageList.get(1), request.getAttachedPhotoIds().get(0), request.getAttachedPhotoIds().get(1));
    }

    @DisplayName("DELETE 이벤트 발생시 reviewHistory 저장")
    @Test
    void reviewHistory_3() {
        // given
        ReviewEventRequest request = ReviewEventRequest.testBuilder()
                .type(EventType.REVIEW)
                .action(Action.DELETE)
                .reviewId("240a0658-dc5f-4878-9381-ebb7b2667772")
                .content("좋아요!")
                .attachedPhotoIds(Arrays.asList("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"))
                .userId("3ede0ef2-92b7-4817-a5f3-0c575361f745")
                .placeId("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
                .build();

        PointType pointType = PointType.of(1, 1, true, 1, 1, true, 1, 1, true);

        // when
        reviewHistoryService.createReviewHistory(request, pointType);

        // then
        List<ReviewHistory> reviewHistoryList = reviewHistoryRepository.findAll();
        List<HistoryImage> historyImageList = historyImageRepository.findAll();
        assertThat(reviewHistoryList).hasSize(1);
        assertThat(historyImageList).hasSize(2);
        TestUtils.assertReviewHistory(reviewHistoryList.get(0), request.getAction(), request.getReviewId(), request.getUserId(), request.getPlaceId());
        TestUtils.assertHistoryImage(historyImageList.get(0), historyImageList.get(1), request.getAttachedPhotoIds().get(0), request.getAttachedPhotoIds().get(1));
    }

}
