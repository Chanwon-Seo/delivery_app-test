package com.sparta.delivery_app.domain.review.repository;

import static com.sparta.delivery_app.domain.order.entity.QOrder.order;
import static com.sparta.delivery_app.domain.review.entity.QManagerReviews.managerReviews;
import static com.sparta.delivery_app.domain.review.entity.QUserReviews.userReviews;
import static com.sparta.delivery_app.domain.store.entity.QStore.store;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.dao.UserReviewsWithManagerReviewsDao;
import com.sparta.delivery_app.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<UserReviewsWithManagerReviewsDao> searchQueryUserReviewByUser(User findUser) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                UserReviewsWithManagerReviewsDao.class,
                                userReviews.id,
                                userReviews.reviewImagePath,
                                userReviews.content,
                                userReviews.createdAt,
                                managerReviews.content,
                                store.storeName
                        ))
                .from(userReviews)
                .join(order).on(userReviews.order.eq(order))
                .join(store).on(order.store.eq(store))
                .leftJoin(managerReviews).on(userReviews.id.eq(managerReviews.userReviews.id))
                .where(
                        userReviews.user.eq(findUser),
                        userReviews.reviewStatus.eq(ReviewStatus.ENABLE)
                ).fetch();

    }
}
