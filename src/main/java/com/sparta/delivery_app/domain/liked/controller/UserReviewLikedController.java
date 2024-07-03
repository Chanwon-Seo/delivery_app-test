package com.sparta.delivery_app.domain.liked.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.liked.service.UserReviewLikedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
