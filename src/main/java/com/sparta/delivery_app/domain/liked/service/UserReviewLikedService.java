package com.sparta.delivery_app.domain.liked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedDuplicatedException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.liked.adapter.UserReviewLikedAdapter;
import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
import com.sparta.delivery_app.domain.review.adapter.UserReviewsAdapter;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserReviewLikedService {

    private final UserAdapter userAdapter;
    private final UserReviewsAdapter userReviewsAdapter;
    private final UserReviewLikedAdapter userReviewLikedAdapter;

    public void addUserReviewLiked(AuthenticationUser user, Long userReviewId) {
        User findUser = userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());

        UserReviews userReviews = userReviewsAdapter.searchQueryUserReviewById(userReviewId);

        if (userReviewLikedAdapter.existsQueryUserReviewLikedByUserAndUserReviews(findUser, userReviews)) {
            throw new LikedDuplicatedException(LikedErrorCode.LIKED_ALREADY_REGISTERED_USER_REVIEW);
        }

        UserReviewLiked userReviewLiked = UserReviewLiked.saveUserReviewLiked(findUser, userReviews);
        userReviewLikedAdapter.saveUserReviewLiked(userReviewLiked);
    }

}
