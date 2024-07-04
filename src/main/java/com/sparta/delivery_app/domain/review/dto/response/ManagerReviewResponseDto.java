package com.sparta.delivery_app.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerReviewResponseDto {

    private String storeName;
    private String content;

    public static ManagerReviewResponseDto of(
            String storeName, String content
    ) {
        return ManagerReviewResponseDto.builder()
                .storeName(storeName)
                .content(content)
                .build();

    }

}
