package com.sparta.delivery_app.domain.openApi.adapter;

import com.sparta.delivery_app.domain.liked.adapter.StoreLikedAdapter;
import com.sparta.delivery_app.domain.liked.repository.dto.LikedStoreTopVO;
import com.sparta.delivery_app.domain.openApi.dto.StoreDetailsResponseDto;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.UserReviewsRepository;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.store.repository.StoreQueryRepository;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenApiAdapter {

    private final StoreRepository storeRepository;
    private final StoreQueryRepository storeQueryRepository;
    private final StoreAdapter storeAdapter;
    private final UserReviewsRepository userReviewsRepository;
    private final StoreLikedAdapter storeLikedAdapter;

    /**
     * ENABLE 상태인 매장만 조회
     *
     * @return
     */
    public Page<Store> queryStores(Pageable pageable) {
        return storeRepository.findAllByStatus(pageable, StoreStatus.ENABLE);
    }

    /**
     * 특정 매장의 정보 조회
     *
     * @param storeId
     * @return
     */
    public StoreDetailsResponseDto queryMenusByStoreId(Long storeId) {

        Store store = storeAdapter.queryStoreById(storeId);
        StoreStatus.checkStoreStatus(store);

        return new StoreDetailsResponseDto(store);
    }

    /**
     * ENABLE 상태인 사용자 리뷰만 조회
     *
     * @param pageable
     * @return
     */
    public Page<UserReviews> queryReviews(Pageable pageable) {
        return userReviewsRepository.findAllByReviewStatus(pageable, ReviewStatus.ENABLE);
    }

    public List<LikedStoreTopVO> searchQueryTopLikedStore(Integer topNum) {
        return storeLikedAdapter.searchQueryTopLikedStore(topNum);
    }
}
