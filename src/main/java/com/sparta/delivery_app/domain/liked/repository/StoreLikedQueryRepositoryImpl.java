package com.sparta.delivery_app.domain.liked.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedMenuWithUserDto;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedStoreTopVO;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedWithUserVO;
import com.sparta.delivery_app.domain.liked.repository.dto.MenuVO;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sparta.delivery_app.domain.liked.entity.QStoreLiked.storeLiked;
import static com.sparta.delivery_app.domain.menu.entity.QMenu.menu;
import static com.sparta.delivery_app.domain.order.entity.QOrder.*;
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

    @Override
    public Page<LikedMenuWithUserDto> searchQueryLikedMenuWithUserAndStoreAndMenuByUser(User findUser, Store findStore, Pageable pageable) {
        Store searchStore = jpaQueryFactory
                .selectFrom(store)
                .where(
                        store.id.eq(findStore.getId())
                )
                .fetchOne();

        List<MenuVO> menuList = jpaQueryFactory
                .select(Projections.constructor(
                        MenuVO.class,
                        menu.id,
                        menu.menuName,
                        menu.menuInfo,
                        menu.menuPrice,
                        menu.menuImagePath

                ))
                .from(storeLiked)
                .join(user).on(storeLiked.user.eq(user))
                .join(store).on(storeLiked.store.eq(store))
                .join(menu).on(store.eq(menu.store))
                .where(
                        storeLiked.user.id.eq(findUser.getId()),
                        store.id.eq(findStore.getId()),
                        menu.menuStatus.eq(MenuStatus.ENABLE)
                )
                .orderBy(menu.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(menu.count())
                .from(storeLiked)
                .join(user).on(storeLiked.user.eq(user))
                .join(store).on(storeLiked.store.eq(store))
                .join(menu).on(store.eq(menu.store))
                .where(
                        storeLiked.user.id.eq(findUser.getId()),
                        store.id.eq(findStore.getId()),
                        menu.menuStatus.eq(MenuStatus.ENABLE)
                );

        LikedMenuWithUserDto likedMenuWithUserDto = LikedMenuWithUserDto.of(searchStore, menuList);
        List<LikedMenuWithUserDto> likedMenuWithUserDtos = Collections.singletonList(likedMenuWithUserDto);

        return PageableExecutionUtils.getPage(likedMenuWithUserDtos, pageable, countQuery::fetchOne);
    }

    @Override
    public List<LikedStoreTopVO> searchQueryTopLikedStore(Integer topNum) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                LikedStoreTopVO.class,
                                store.storeName,
                                store.storeInfo,
                                store.minTotalPrice,
                                store.storeAddress,
                                storeLiked.count(),
                                store.menuList.size(),
                                order.count()
                        ))
                .from(storeLiked)
                .join(store).on(storeLiked.store.eq(store))
                .join(order).on(order.store.eq(store))
                .groupBy(store.id)
                .where(
                        store.status.eq(StoreStatus.ENABLE)
                )
                .orderBy(storeLiked.count().desc())
                .limit(topNum)
                .fetch();
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
