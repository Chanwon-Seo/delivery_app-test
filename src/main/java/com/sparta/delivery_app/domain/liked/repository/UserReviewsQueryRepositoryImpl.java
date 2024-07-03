package com.sparta.delivery_app.domain.liked.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.delivery_app.domain.review.entity.QUserReviews.userReviews;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReviewsQueryRepositoryImpl implements UserReviewsQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsQueryByUserReviewId(Long userReviewId) {
        UserReviews userReviewLiked = jpaQueryFactory
                .selectFrom(userReviews)
                .where(
                        userReviews.id.eq(userReviewId),
                        userReviews.reviewStatus.eq(ReviewStatus.ENABLE)
                ).fetchFirst();

        return userReviewLiked != null;
    }

    @Override
    public Optional<UserReviews> searchQueryUserReviewById(Long userReviewId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(userReviews)
                .where(
                        userReviews.id.eq(userReviewId),
                        userReviews.reviewStatus.eq(ReviewStatus.ENABLE)
                ).fetchFirst());
    }
}
