package com.sparta.delivery_app.domain.liked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.ReviewNotFoundException;
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
        // 유저 검증
        User findUser = userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());

        // 해당 리뷰 존재 여부 검증
        UserReviews userReviews = userReviewsAdapter.searchQueryUserReviewById(userReviewId);

        // 이미 좋아요를 등록한 리뷰인지 검증
        if (userReviewLikedAdapter.existsQueryUserReviewLikedByUserAndId(userReviewId)) {
            throw new LikedDuplicatedException(LikedErrorCode.LIKED_ALREADY_REGISTERED_USER_REVIEW);
        }

        UserReviewLiked userReviewLiked = UserReviewLiked.saveUserReviewLiked(findUser, userReviews);
        userReviewLikedAdapter.saveUserReviewLiked(userReviewLiked);
    }
}
