package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.review.entity.UserReviews;

import java.util.Optional;

public interface UserReviewsQueryRepository {
    boolean existsQueryByUserReviewId(Long userReviewId);

    Optional<UserReviews> searchQueryUserReviewById(Long userReviewId);
}
