package com.sparta.delivery_app.domain.review.repository;

import static com.sparta.delivery_app.domain.order.entity.QOrder.order;
import static com.sparta.delivery_app.domain.review.entity.QManagerReviews.managerReviews;
import static com.sparta.delivery_app.domain.review.entity.QUserReviews.userReviews;
import static com.sparta.delivery_app.domain.store.entity.QStore.store;
import static com.sparta.delivery_app.domain.user.entity.QUser.user;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.dao.ReviewDao;
import com.sparta.delivery_app.domain.review.repository.dao.ReviewsFilterDao;
import com.sparta.delivery_app.domain.review.repository.dao.UserReviewsWithManagerReviewsDao;
import com.sparta.delivery_app.domain.store.dto.request.StoreReviewsSearchCondition;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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

    @Override
    public Page<ReviewsFilterDao> searchQueryReviewsFilterPage(
            StoreReviewsSearchCondition codi,
            Store findStore,
            Pageable pageable) {

        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(codi);
        List<ReviewsFilterDao> resuts = jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReviewsFilterDao.class,
                                order.id,
                                store.storeName,
                                Projections.list(Projections.constructor(
                                        ReviewDao.class,
                                        user.nickName,
                                        userReviews.id,
                                        userReviews.content,
                                        userReviews.reviewImagePath,
                                        userReviews.createdAt,
                                        userReviews.rating,
                                        managerReviews.content
                                ))
                        ))
                .from(user)
                .join(order).on(order.user.id.eq(user.id))
                .join(store).on(order.store.id.eq(store.id))
//                .join(orderItem).on(order.id.eq(orderItem.order.id))
//                .join(menu).on(orderItem.menu.id.eq(menu.id))
                .join(userReviews).on(order.id.eq(userReviews.order.id))
                .leftJoin(managerReviews).on(userReviews.id.eq(managerReviews.userReviews.id))
                .fetchJoin()
                .where(
                        userReviews.reviewStatus.eq(ReviewStatus.ENABLE),
                        store.status.eq(StoreStatus.ENABLE),
                        userReviewImageEq(codi.isPhoto())
                )
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(userReviews.id)
                .from(user)
                .join(order).on(order.user.id.eq(user.id))
                .join(store).on(order.store.id.eq(store.id))
//                .join(orderItem).on(order.id.eq(orderItem.order.id))
//                .join(menu).on(orderItem.menu.id.eq(menu.id))
                .join(userReviews).on(order.id.eq(userReviews.order.id))
                .leftJoin(managerReviews).on(userReviews.id.eq(managerReviews.userReviews.id))
                .where(
                        userReviews.reviewStatus.eq(ReviewStatus.ENABLE),
                        store.status.eq(StoreStatus.ENABLE),
                        userReviewImageEq(codi.isPhoto())
                );

        return PageableExecutionUtils.getPage(resuts, pageable, countQuery::fetchOne);
    }


    private BooleanExpression userReviewImageEq(Boolean hasPhoto) {
        return hasPhoto ? userReviews.reviewImagePath.isNotNull()
                : userReviews.reviewImagePath.isNull();
    }

    private OrderSpecifier[] createOrderSpecifier(StoreReviewsSearchCondition orderCondition) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        OrderSpecifier<LocalDateTime> localDateTimeOrderSpecifier =
                orderCondition.isCreated() ?
                        userReviews.createdAt.asc() : userReviews.createdAt.desc();

        OrderSpecifier<Integer> integerOrderSpecifier =
                orderCondition.isRating() ?
                        userReviews.rating.asc() : userReviews.rating.desc();

        orderSpecifiers.add(localDateTimeOrderSpecifier);
        orderSpecifiers.add(integerOrderSpecifier);

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
