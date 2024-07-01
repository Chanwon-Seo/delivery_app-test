package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;

public interface StoreLikedQueryRepository {
    boolean existsQueryByStoreAndUser(Store store, User user);
}
