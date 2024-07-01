package com.sparta.delivery_app.domain.liked.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.delivery_app.domain.liked.entity.QStoreLiked.storeLiked;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreQueryRepositoryImpl implements StoreLikedQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public boolean existsQueryByStoreAndUser(Store store, User user) {
        StoreLiked storeLiked1 = query(store, user);
        return storeLiked1 != null;
    }

    @Override
    public Optional<StoreLiked> searchQueryLikedByStoreAndUser(Store store, User user) {
        return Optional.ofNullable(query(store, user));
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
