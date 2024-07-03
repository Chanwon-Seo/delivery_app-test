package com.sparta.delivery_app.domain.liked.repository.dto;

import java.time.LocalDateTime;

public record UserReviewLikedVO(
        String nickName,
        String userReviewContent,
        LocalDateTime createAt
) {
}
