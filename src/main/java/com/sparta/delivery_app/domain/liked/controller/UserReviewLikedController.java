package com.sparta.delivery_app.domain.liked.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.liked.dto.response.UserReviewLikedResponseDto;
import com.sparta.delivery_app.domain.liked.service.UserReviewLikedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/reviews")
public class UserReviewLikedController {

    private final UserReviewLikedService userReviewLikedService;

    /**
     * 리뷰 좋아요 등록
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/user-review/{userReviewId}")
    public ResponseEntity<RestApiResponse<String>> likedAdd(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long userReviewId
    ) {
        userReviewLikedService.addUserReviewLiked(user, userReviewId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("해당 댓글에 좋아요를 등록하였습니다."));
    }

    /**
     * 리뷰 좋아요 삭제
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @DeleteMapping("/user-review/{userReviewLikedId}")
    public ResponseEntity<RestApiResponse<String>> likedDelete(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long userReviewLikedId
    ) {
        userReviewLikedService.deleteUserReviewLiked(user, userReviewLikedId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("해당 댓글에 좋아요를 삭제하였습니다."));
    }

    /**
     * 리뷰 좋아요 목록 조회
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/user-review")
    public ResponseEntity<RestApiResponse<UserReviewLikedResponseDto>> likedDelete(
            @AuthenticationPrincipal AuthenticationUser user,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum
    ) {
        UserReviewLikedResponseDto userReviewLiked = userReviewLikedService.getUserReviewLiked(user, pageNum);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of(userReviewLiked));
    }

}
