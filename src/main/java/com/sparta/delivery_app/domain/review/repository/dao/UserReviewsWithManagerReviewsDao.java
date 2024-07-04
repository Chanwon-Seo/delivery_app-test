package com.sparta.delivery_app.domain.review.repository.dao;

import java.time.LocalDateTime;

public record UserReviewsWithManagerReviewsDao(
        Long userReviewId,
        String userReviewImagePath,
        String userReviewContent,
        LocalDateTime createdAt,
        String managerReviewContent,
        String storeName
) {

}
