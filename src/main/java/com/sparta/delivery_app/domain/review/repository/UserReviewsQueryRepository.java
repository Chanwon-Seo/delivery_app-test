package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.dao.UserReviewsWithManagerReviewsDao;
import com.sparta.delivery_app.domain.user.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserReviewsQueryRepository {

    boolean existsQueryByUserReviewId(Long userReviewId);

    Optional<UserReviews> searchQueryUserReviewById(Long userReviewId);

    List<UserReviewsWithManagerReviewsDao> searchQueryUserReviewByUser(User user);
}
