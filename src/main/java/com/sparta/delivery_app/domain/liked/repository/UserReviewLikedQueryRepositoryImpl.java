package com.sparta.delivery_app.domain.liked.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.delivery_app.domain.liked.entity.QUserReviewLiked.userReviewLiked;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReviewLikedQueryRepositoryImpl implements UserReviewLikedQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsQueryUserReviewLikedByUserAndUserReviews(User user, UserReviews userReviews) {
        UserReviewLiked findUserReviewLiked = jpaQueryFactory.selectFrom(userReviewLiked)
                .where(
                        userReviewLiked.user.eq(user),
                        userReviewLiked.userReviews.eq(userReviews)
                )
                .fetchOne();
        return findUserReviewLiked != null;
    }

}
