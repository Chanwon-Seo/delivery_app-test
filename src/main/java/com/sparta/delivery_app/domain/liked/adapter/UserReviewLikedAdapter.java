package com.sparta.delivery_app.domain.liked.adapter;

import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
import com.sparta.delivery_app.domain.liked.repository.UserReviewLikedRepository;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReviewLikedAdapter {

    private final UserReviewLikedRepository userReviewLikedRepository;

    public boolean existsQueryUserReviewLikedByUserAndUserReviews(User findUser, UserReviews userReviewId) {
        return userReviewLikedRepository.existsQueryUserReviewLikedByUserAndUserReviews(findUser, userReviewId);
    }

    public void saveUserReviewLiked(UserReviewLiked userReviewLiked) {
        userReviewLikedRepository.save(userReviewLiked);
    }
}
