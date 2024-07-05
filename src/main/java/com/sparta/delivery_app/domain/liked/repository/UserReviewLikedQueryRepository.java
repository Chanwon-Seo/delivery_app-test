package com.sparta.delivery_app.domain.liked.repository;

import static com.sparta.delivery_app.domain.liked.entity.QUserReviewLiked.userReviewLiked;
import static com.sparta.delivery_app.domain.review.entity.QUserReviews.userReviews;
import static com.sparta.delivery_app.domain.user.entity.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
import com.sparta.delivery_app.domain.liked.repository.dto.UserReviewLikedVO;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserReviewLikedQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public boolean existsQueryUserReviewLikedByUserAndUserReviews(User user,
            UserReviews userReviews) {
        UserReviewLiked findUserReviewLiked = jpaQueryFactory.selectFrom(userReviewLiked)
                .where(
                        userReviewLiked.user.eq(user),
                        userReviewLiked.userReviews.eq(userReviews)
                )
                .fetchOne();
        return findUserReviewLiked != null;
    }

    public Optional<UserReviewLiked> searchQueryUserReviewLikedById(Long userReviewLikedId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(userReviewLiked)
                .where(
                        userReviewLiked.id.eq(userReviewLikedId)
                ).fetchOne());
    }

    public Page<UserReviewLikedVO> searchQueryUserReviewLikedByUser(User findUser,
            Pageable pageable) {
        List<UserReviewLikedVO> userReviewLikedList = jpaQueryFactory
                .select(
                        Projections.constructor(
                                UserReviewLikedVO.class,
                                user.nickName,
                                userReviews.content,
                                userReviews.createdAt
                        ))
                .from(userReviewLiked)
                .where(
                        userReviewLiked.user.eq(findUser),
                        userReviews.reviewStatus.eq(ReviewStatus.ENABLE)
                )
                .join(userReviews).on(userReviewLiked.userReviews.eq(userReviews))
                .join(user).on(userReviews.user.eq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(userReviewLiked.count())
                .from(userReviewLiked)
                .where(
                        userReviewLiked.user.eq(findUser),
                        userReviews.reviewStatus.eq(ReviewStatus.ENABLE)
                )
                .join(userReviews).on(userReviewLiked.userReviews.eq(userReviews))
                .join(user).on(userReviews.user.eq(user));
        return PageableExecutionUtils.getPage(userReviewLikedList, pageable, countQuery::fetchOne);
    }

}
