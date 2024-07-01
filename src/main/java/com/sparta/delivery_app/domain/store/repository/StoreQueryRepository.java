package com.sparta.delivery_app.domain.store.repository;

import com.sparta.delivery_app.domain.store.entity.Store;

import java.util.Optional;

public interface StoreQueryRepository {
    Optional<Store> searchQueryById(Long storeId);
}
