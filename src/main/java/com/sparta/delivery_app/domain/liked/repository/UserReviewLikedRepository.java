package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReviewLikedRepository extends JpaRepository<UserReviewLiked, Long>, UserReviewLikedQueryRepository{

}
