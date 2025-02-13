package com.sparta.delivery_app.domain.liked.adapter;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedNotFoundException;
import com.sparta.delivery_app.domain.liked.entity.StoreLiked;
import com.sparta.delivery_app.domain.liked.repository.StoreLikedQueryRepository;
import com.sparta.delivery_app.domain.liked.repository.StoreLikedRepository;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedMenuWithUserDto;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedStoreTopVO;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedWithUserVO;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreLikedAdapter {

    private final StoreLikedRepository storeLikedRepository;
    private final StoreLikedQueryRepository storeLikedQueryRepository;

    /**
     * QueryDsl
     */
    public boolean existsQueryUserByStoreAndUser(Store store, User user) {
        return storeLikedQueryRepository.existsQueryByStoreAndUser(store, user);
    }

    public StoreLiked searchQueryLikedByStoreAndUser(Store store, User user) {
        return storeLikedQueryRepository.searchQueryLikedByStoreAndUser(store, user)
                .orElseThrow(() ->
                        new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_STORE));
    }

    public Page<LikedWithUserVO> searchQueryLikedWithUserAndStorePage(User user,
            Pageable pageable) {
        return storeLikedQueryRepository.searchQueryLikedWithUserAndStoreByUser(user, pageable);
    }

    public Page<LikedMenuWithUserDto> searchQueryLikedMenuWithUserAndStoreAndMenuPage(User findUser,
            Store findStore, Pageable pageable) {
        return storeLikedQueryRepository.searchQueryLikedMenuWithUserAndStoreAndMenuByUser(findUser,
                findStore, pageable);
    }

    public List<LikedStoreTopVO> searchQueryTopLikedStore(Integer topNum) {
        return storeLikedQueryRepository.searchQueryTopLikedStore(topNum);
    }

    /**
     * JPA
     */
    public void saveLiked(StoreLiked storeLiked) {
        storeLikedRepository.save(storeLiked);
    }

    public void deleteLiked(StoreLiked storeLiked) {
        storeLikedRepository.delete(storeLiked);
    }

    public StoreLiked queryLikedByStoreId(Long storeId) {
        return storeLikedRepository.findByStoreId(storeId).orElseThrow(() ->
                new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_STORE));
    }


    public boolean existsByStoreAndUser(Store store, User user) {
        return storeLikedRepository.existsByStoreAndUser(store, user);
    }
}
