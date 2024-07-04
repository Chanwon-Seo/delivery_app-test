package com.sparta.delivery_app.domain.review.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewAddRequestDto;
import com.sparta.delivery_app.domain.review.dto.request.UserReviewModifyRequestDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewAddResponseDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewModifyResponseDto;
import com.sparta.delivery_app.domain.review.dto.response.UserReviewsAndManagerReviewsResponseDto;
import com.sparta.delivery_app.domain.review.service.UserReviewsService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews/consumers")
public class UserReviewsController {

    private final UserReviewsService userReviewsService;

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<UserReviewAddResponseDto>> create(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable final Long orderId,
            @Valid @RequestPart final UserReviewAddRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser user) {

        UserReviewAddResponseDto responseDto = userReviewsService.addReview(file, orderId,
                requestDto, user);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("리뷰가 등록되었습니다.", responseDto));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PatchMapping("/{orderId}")
    public ResponseEntity<RestApiResponse<UserReviewModifyResponseDto>> update(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable final Long orderId,
            @Valid @RequestPart final UserReviewModifyRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser user) {

        UserReviewModifyResponseDto responseDto = userReviewsService.modifyReview(file, orderId,
                requestDto, user);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("리뷰가 수정되었습니다.", responseDto));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @DeleteMapping("{reviewId}")
    public ResponseEntity<RestApiResponse<Object>> delete(@PathVariable final Long reviewId,
            @AuthenticationPrincipal AuthenticationUser user) {

        userReviewsService.deleteReview(reviewId, user);

        return ResponseEntity.status(StatusCode.OK.code).body(RestApiResponse.of("리뷰가 삭제되었습니다."));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping
    public ResponseEntity<RestApiResponse<Object>> getReviews(
            @AuthenticationPrincipal AuthenticationUser user) {

        List<UserReviewsAndManagerReviewsResponseDto> responseDto = userReviewsService
                .getReviews(user);

        return ResponseEntity.status(StatusCode.OK.code).body(RestApiResponse.of(responseDto));
    }
}
