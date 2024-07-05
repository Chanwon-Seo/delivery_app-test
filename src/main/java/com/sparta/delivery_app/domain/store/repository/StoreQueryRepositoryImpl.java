package com.sparta.delivery_app.domain.store.repository;

import static com.sparta.delivery_app.domain.store.entity.QStore.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.store.entity.Store;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
