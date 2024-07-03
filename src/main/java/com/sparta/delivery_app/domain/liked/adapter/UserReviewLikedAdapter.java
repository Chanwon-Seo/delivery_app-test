package com.sparta.delivery_app.domain.liked.adapter;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedNotFoundException;
import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
import com.sparta.delivery_app.domain.liked.repository.UserReviewLikedRepository;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    public UserReviewLiked searchQueryUserReviewLikedById(Long userReviewLikedId) {
        return userReviewLikedRepository.searchQueryUserReviewLikedById(userReviewLikedId).orElseThrow(() ->
                new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_USER_REVIEW));
    }

    public void deleteUserReviewLiked(UserReviewLiked findUserReviewLiked) {
        userReviewLikedRepository.delete(findUserReviewLiked);
    }
}
