package com.sparta.delivery_app.domain.liked.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.liked.dto.response.LikesMenuResponseDto;
import com.sparta.delivery_app.domain.liked.dto.response.LikesResponseDto;
import com.sparta.delivery_app.domain.liked.service.StoreLikedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/liked")
public class StoreLikedController {

    private final StoreLikedService storeLikedService;

    /**
     * 좋아요 등록
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/stores/{storeId}")
    public ResponseEntity<RestApiResponse<String>> likedAdd(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long storeId
    ) {
        storeLikedService.addLiked(user, storeId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("관심 매장으로 등록되었습니다."));
    }

    /**
     * 좋아요 삭제
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @DeleteMapping("/stores/{storeId}")
    public ResponseEntity<RestApiResponse<String>> likedDelete(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long storeId
    ) {
        storeLikedService.deleteLiked(user, storeId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("관심 매장 등록이 취소되었습니다."));
    }

    /**
     * 관심 매장 페이징 조회
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/stores")
    public ResponseEntity<RestApiResponse<LikesResponseDto>> likedPage(
            @AuthenticationPrincipal AuthenticationUser user,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum
    ) {
        LikesResponseDto responseDto = storeLikedService.findLikes(user, pageNum);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of(responseDto));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<RestApiResponse<LikesMenuResponseDto>> likedMenuPage(
            @AuthenticationPrincipal AuthenticationUser user,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
            @PathVariable final Long storeId
    ) {
        LikesMenuResponseDto responseDto = storeLikedService.likedMenuPage(user, pageNum, storeId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of(responseDto));
    }
}
