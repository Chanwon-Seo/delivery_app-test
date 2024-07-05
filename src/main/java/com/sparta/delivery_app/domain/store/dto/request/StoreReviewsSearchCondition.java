package com.sparta.delivery_app.domain.store.dto.request;

import lombok.Builder;

@Builder
public record StoreReviewsSearchCondition(
        Boolean isCreated,
        Boolean isPhoto,
        Boolean isRating
) {

    public static StoreReviewsSearchCondition of(StoreReviewsRequestDto requestDto) {
        return StoreReviewsSearchCondition.builder()
                .isPhoto(requestDto.getHasPhoto())
                .isCreated(requestDto.getIsCreated())
                .isRating(requestDto.getHasRating())
                .build();
    }
}
