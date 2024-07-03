package com.sparta.delivery_app.domain.liked.repository;

import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedMenuWithUserDto;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedStoreTopVO;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedWithUserVO;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StoreLikedQueryRepository {
    boolean existsQueryByStoreAndUser(Store store, User user);

    Optional<StoreLiked> searchQueryLikedByStoreAndUser(Store store, User findUser);

    Page<LikedWithUserVO> searchQueryLikedWithUserAndStoreByUser(User user, Pageable pageable);

    Page<LikedMenuWithUserDto> searchQueryLikedMenuWithUserAndStoreAndMenuByUser(User findUser, Store findStore, Pageable pageable);

    List<LikedStoreTopVO> searchQueryTopLikedStore(Integer topNum);
}
