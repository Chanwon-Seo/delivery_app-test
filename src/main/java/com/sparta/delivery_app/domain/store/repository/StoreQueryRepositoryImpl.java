package com.sparta.delivery_app.domain.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.delivery_app.domain.store.entity.QStore.store;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryRepositoryImpl implements StoreQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Store> searchQueryById(Long storeId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(store)
                        .where(store.id.eq(storeId))
                        .fetchOne());
    }
}
