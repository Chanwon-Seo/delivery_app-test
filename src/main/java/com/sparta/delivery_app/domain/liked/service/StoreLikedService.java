package com.sparta.delivery_app.domain.liked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedDuplicatedException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.liked.adapter.StoreLikedAdapter;
import com.sparta.delivery_app.domain.liked.dto.response.LikesMenuResponseDto;
import com.sparta.delivery_app.domain.liked.dto.response.LikesResponseDto;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedMenuWithUserDto;
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
    private final StoreLikedAdapter storeLikedAdapter;


    public void addLiked(AuthenticationUser user, final Long storeId) {
        User findUser = userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());
        Store store = storeAdapter.searchQueryStoreById(storeId);

        if (storeLikedAdapter.existsQueryUserByStoreAndUser(store, findUser)) {
            throw new LikedDuplicatedException(LikedErrorCode.LIKED_ALREADY_REGISTERED_STORE);
        }

        StoreLiked storeLiked = StoreLiked.saveStoreLiked(store, findUser);
        storeLikedAdapter.saveLiked(storeLiked);
    }

    @Transactional
    public void deleteLiked(AuthenticationUser user, final Long storeId) {
        User findUser = userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());
        Store store = storeAdapter.searchQueryStoreById(storeId);

        StoreLiked storeLiked = storeLikedAdapter.searchQueryLikedByStoreAndUser(store, findUser);

        storeLikedAdapter.deleteLiked(storeLiked);
    }

    @Transactional(readOnly = true)
    public LikesResponseDto findLikes(AuthenticationUser user, Integer pageNum) {
        User findUser = userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, Boolean.TRUE);
        Page<LikedWithUserVO> likedWithUserDto = storeLikedAdapter.searchQueryLikedWithUserAndStorePage(findUser, pageable);

        return LikesResponseDto.of(likedWithUserDto);
    }

    @Transactional(readOnly = true)
    public LikesMenuResponseDto likedMenuPage(AuthenticationUser user, Integer pageNum, Long storeId) {
        User findUser = userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());
        Store findStore = storeAdapter.searchQueryStoreById(storeId);

        if (!storeLikedAdapter.existsQueryUserByStoreAndUser(findStore, findUser)) {
            throw new LikedDuplicatedException(LikedErrorCode.LIKED_UNREGISTERED_STORE);
        }

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, Boolean.TRUE);
        Page<LikedMenuWithUserDto> likedMenuWithUserVO = storeLikedAdapter.searchQueryLikedMenuWithUserAndStoreAndMenuPage(findUser, findStore, pageable);

        return LikesMenuResponseDto.of(likedMenuWithUserVO);
    }
}
