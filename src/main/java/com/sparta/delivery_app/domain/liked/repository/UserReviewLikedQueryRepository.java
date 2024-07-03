package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.User;

import java.util.Optional;

public interface UserReviewLikedQueryRepository {

    boolean existsQueryUserReviewLikedByUserAndUserReviews(User findUser, UserReviews userReviewId);

    Optional<UserReviewLiked> searchQueryUserReviewLikedById(Long userReviewLikedId);
}
