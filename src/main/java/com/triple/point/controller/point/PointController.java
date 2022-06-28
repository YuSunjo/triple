package com.triple.point.controller.point;

import com.triple.point.ApiResponse;
import com.triple.point.dto.point.PointInfoResponse;
import com.triple.point.dto.point.UserPointRequest;
import com.triple.point.service.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    // 포인트 조회 API
    @GetMapping("user/point")
    public ApiResponse<PointInfoResponse> getUserPoint(UserPointRequest request) {
        pointService.getUserPoint(request.getUserId());
        return ApiResponse.success(pointService.getUserPoint(request.getUserId()));
    }

}
