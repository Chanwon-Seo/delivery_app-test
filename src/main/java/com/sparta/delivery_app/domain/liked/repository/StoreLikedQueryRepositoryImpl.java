package com.sparta.delivery_app.domain.liked.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedWithUserVO;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sparta.delivery_app.domain.liked.entity.QStoreLiked.storeLiked;
import static com.sparta.delivery_app.domain.store.entity.QStore.store;
import static com.sparta.delivery_app.domain.user.entity.QUser.user;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreLikedQueryRepositoryImpl implements StoreLikedQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public boolean existsQueryByStoreAndUser(Store store, User user) {
        StoreLiked storeLiked = query(store, user);
        return storeLiked != null;
    }

    @Override
    public Optional<StoreLiked> searchQueryLikedByStoreAndUser(Store store, User user) {
        return Optional.ofNullable(query(store, user));
    }

    public Page<LikedWithUserVO> searchQueryLikedWithUserAndStoreByUser(User findUser, Pageable pageable) {
        List<LikedWithUserVO> likes = jpaQueryFactory
                .select(Projections.constructor(
                        LikedWithUserVO.class,
                        store.id,
                        store.storeName,
                        store.storeAddress
                ))
                .from(storeLiked)
                .join(store).on(storeLiked.store.eq(store))
                .join(user).on(storeLiked.user.eq(user))
                .where(
                        storeLiked.user.id.eq(findUser.getId())
                )
                .orderBy(storeLiked.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(storeLiked.count())
                .from(storeLiked)
                .join(store).on(storeLiked.store.eq(store))
                .join(user).on(storeLiked.user.eq(user))
                .where(
                        storeLiked.user.id.eq(findUser.getId())
                );

        return PageableExecutionUtils.getPage(likes, pageable, countQuery::fetchOne);
    }

    private StoreLiked query(Store store, User user) {
        return jpaQueryFactory
                .selectFrom(storeLiked)
                .where(
                        storeLiked.store.eq(store),
                        storeLiked.user.eq(user)
                )
                .fetchFirst();
    }

}
