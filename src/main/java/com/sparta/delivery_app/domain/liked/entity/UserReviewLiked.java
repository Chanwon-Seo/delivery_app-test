package com.sparta.delivery_app.domain.liked.entity;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.dto.request.ConsumersSignupRequestDto;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReviewLiked extends Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_review_liked_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_liked_id", nullable = false)
    private UserReviews userReviews;

    @Builder
    public UserReviewLiked(User user, UserReviews userReviews) {
        super(user);
        this.userReviews = userReviews;
    }

    public static UserReviewLiked saveUserReviewLiked(User user, UserReviews userReviews) {
        return UserReviewLiked.builder()
                .user(user)
                .userReviews(userReviews)
                .build();
    }

}
