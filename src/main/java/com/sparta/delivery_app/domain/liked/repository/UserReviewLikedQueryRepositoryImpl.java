package com.sparta.delivery_app.domain.liked.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.liked.entity.UserReviewLiked;
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
    public boolean existsQueryUserReviewLikedByUserAndId(Long userReviewId) {
        UserReviewLiked findUserReviewLiked = jpaQueryFactory.selectFrom(userReviewLiked)
                .where(userReviewLiked.id.eq(userReviewId))
                .fetchOne();
        return findUserReviewLiked != null;
    }

}
