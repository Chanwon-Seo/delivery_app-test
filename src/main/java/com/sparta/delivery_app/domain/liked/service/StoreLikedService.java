package com.sparta.delivery_app.domain.liked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedDuplicatedException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.liked.adapter.LikedAdapter;
import com.sparta.delivery_app.domain.liked.dto.response.LikesResponseDto;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedWithUserVO;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreLikedService {

    private final StoreAdapter storeAdapter;
    private final UserAdapter userAdapter;
    private final LikedAdapter likedAdapter;


    public void addLiked(AuthenticationUser user, final Long storeId) {
        User findUser = userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());
        Store store = storeAdapter.searchQueryStoreById(storeId);

        if (likedAdapter.existsQueryUserByStoreAndUser(store, findUser)) {
            throw new LikedDuplicatedException(LikedErrorCode.LIKED_ALREADY_REGISTERED_ERROR);
        }

        StoreLiked storeLiked = StoreLiked.saveStoreLiked(store, findUser);
        likedAdapter.saveLiked(storeLiked);
    }

    @Transactional
    public void deleteLiked(AuthenticationUser user, final Long storeId) {
        User findUser = userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());
        Store store = storeAdapter.searchQueryStoreById(storeId);

        StoreLiked storeLiked = likedAdapter.searchQueryLikedByStoreAndUser(store, findUser);

        likedAdapter.deleteLiked(storeLiked);
    }

    @Transactional(readOnly = true)
    public LikesResponseDto findLikes(AuthenticationUser user, Integer pageNum) {
        User findUser = userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, Boolean.TRUE);
        Page<LikedWithUserVO> likedWithUserDto = likedAdapter.searchQueryLikedWithUserAndStorePage(findUser, pageable);

        return LikesResponseDto.of(likedWithUserDto);
    }

}
