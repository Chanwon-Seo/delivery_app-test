package com.sparta.delivery_app.domain.liked.adapter;

import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
import com.sparta.delivery_app.domain.liked.repository.UserReviewLikedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReviewLikedAdapter {

    private final UserReviewLikedRepository userReviewLikedRepository;

    public boolean existsQueryUserReviewLikedByUserAndId(Long userReviewId) {
        return userReviewLikedRepository.existsQueryUserReviewLikedByUserAndId(userReviewId);
    }

    public void saveUserReviewLiked(UserReviewLiked userReviewLiked) {
        userReviewLikedRepository.save(userReviewLiked);
    }
}
