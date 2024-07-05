package com.sparta.delivery_app.domain.store.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.store.dto.request.ModifyStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.request.StoreReviewsRequestDto;
import com.sparta.delivery_app.domain.store.dto.request.StoreReviewsSearchCondition;
import com.sparta.delivery_app.domain.store.dto.response.ModifyStoreResponseDto;
import com.sparta.delivery_app.domain.store.dto.response.RegisterStoreResponseDto;
import com.sparta.delivery_app.domain.store.dto.response.ReviewsFilterResponseDto;
import com.sparta.delivery_app.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    /*
     * Manager(Status.ENABLE)가 매장 등록(계정 당 1개)
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<RestApiResponse<RegisterStoreResponseDto>> registerStore(
            @Valid @RequestBody final RegisterStoreRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser authenticationUser) {
        log.info("controller-registerStore");
        RegisterStoreResponseDto responseDto = storeService.registerStore(requestDto,
                authenticationUser);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("매장 등록이 완료되었습니다.", responseDto));

    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping
    public ResponseEntity<RestApiResponse<ModifyStoreResponseDto>> modifyStore(
            @Valid @RequestBody final ModifyStoreRequestDto requestDto,
            @AuthenticationPrincipal AuthenticationUser authenticationUser) {
        log.info("controller-modifyStore");
        ModifyStoreResponseDto responseDto = storeService.modifyStore(requestDto,
                authenticationUser);

        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("매장 정보 수정이 완료되었습니다.", responseDto));

    }

    /**
     * 매장 리뷰 필터 조회
     *
     * @return
     */
    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/{storeId}/reviews")
    public ResponseEntity<RestApiResponse<ReviewsFilterResponseDto>> getStoreReviews(
            @AuthenticationPrincipal AuthenticationUser user,
            @ModelAttribute StoreReviewsRequestDto requestDto,
            @PathVariable Long storeId
    ) {
        StoreReviewsSearchCondition storeReviewsDto = StoreReviewsSearchCondition
                .of(requestDto);
        ReviewsFilterResponseDto storeReviews = storeService
                .getStoreReviews(user, storeReviewsDto, storeId, requestDto.getPageNum());

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of(storeReviews));
    }
}
