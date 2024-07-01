package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;

import java.util.Optional;

public interface StoreLikedQueryRepository {
    boolean existsQueryByStoreAndUser(Store store, User user);

    Optional<StoreLiked> searchQueryLikedByStoreAndUser(Store store, User findUser);
}
