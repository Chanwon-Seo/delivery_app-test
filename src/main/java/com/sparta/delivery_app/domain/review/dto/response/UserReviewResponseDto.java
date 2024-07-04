package com.sparta.delivery_app.domain.review.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReviewResponseDto {

    private Long reviewId;
    private String imagePath;
    private String content;
    private LocalDateTime createAt;

    public static UserReviewResponseDto of(
            Long reviewId, String imagePath, String content, LocalDateTime createAt
    ) {
        return UserReviewResponseDto.builder()
                .reviewId(reviewId)
                .imagePath(imagePath)
                .content(content)
                .createAt(createAt)
                .build();
    }
}
