package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.User;

public interface UserReviewLikedQueryRepository {

    boolean existsQueryUserReviewLikedByUserAndUserReviews(User findUser, UserReviews userReviewId);
}
