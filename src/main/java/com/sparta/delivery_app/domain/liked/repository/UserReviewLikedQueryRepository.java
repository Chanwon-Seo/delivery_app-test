package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
import com.sparta.delivery_app.domain.liked.repository.dto.UserReviewLikedVO;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserReviewLikedQueryRepository {

    boolean existsQueryUserReviewLikedByUserAndUserReviews(User findUser, UserReviews userReviewId);

    Optional<UserReviewLiked> searchQueryUserReviewLikedById(Long userReviewLikedId);

    Page<UserReviewLikedVO> searchQueryUserReviewLikedByUser(User findUser, Pageable pageable);
}
