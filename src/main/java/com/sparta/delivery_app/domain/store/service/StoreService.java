package com.sparta.delivery_app.domain.store.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adapter.MenuAdapter;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.review.adapter.UserReviewsAdapter;
import com.sparta.delivery_app.domain.review.repository.dao.ReviewsFilterDao;
import com.sparta.delivery_app.domain.store.adapter.StoreAdapter;
import com.sparta.delivery_app.domain.store.dto.request.ModifyStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.request.RegisterStoreRequestDto;
import com.sparta.delivery_app.domain.store.dto.request.StoreReviewsSearchCondition;
import com.sparta.delivery_app.domain.store.dto.response.ModifyStoreResponseDto;
import com.sparta.delivery_app.domain.store.dto.response.RegisterStoreResponseDto;
import com.sparta.delivery_app.domain.store.dto.response.ReviewsFilterResponseDto;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreAdapter storeAdapter;
    private final UserAdapter userAdapter;
    private final UserReviewsAdapter userReviewsAdapter;
    private final MenuAdapter menuAdapter;

    //회원가입 후 로그인한 유저가 매장 등록
    public RegisterStoreResponseDto registerStore(final RegisterStoreRequestDto requestDto,
            AuthenticationUser authenticationUser) {
        log.info("Service-Register Store");
        String email = authenticationUser.getUsername();
        User user = userAdapter.queryUserByEmail(email);
        Long userId = user.getId();

        log.info("Service-Check Registration Authority");
        User checkedManager = userAdapter.checkManagerRole(userId);
        storeAdapter.queryStoreHistory(checkedManager);
        storeAdapter.queryStoreRegistrationNumber(checkedManager,
                requestDto.storeRegistrationNumber());
        Store newStore = storeAdapter.saveNewStore(requestDto, user);

        return RegisterStoreResponseDto.of(newStore);
    }

    /**
     *
     */
    @Transactional
    public ModifyStoreResponseDto modifyStore(final ModifyStoreRequestDto requestDto,
            AuthenticationUser authenticationUser) {
        log.info("Service-ModifyStore");
        String email = authenticationUser.getUsername();
        User user = userAdapter.queryUserByEmail(email);
        Long userId = user.getId();

        log.info("Service-ENABLE 상태인 MANAGER 소유의 Store 확인");
        User checkStoreOwner = userAdapter.checkManagerRole(userId);
        Store ownedStore = storeAdapter.queryStoreId(checkStoreOwner);
        StoreStatus.checkStoreStatus(ownedStore);
        ownedStore.modifyStore(requestDto);
        storeAdapter.saveModifiedStore(ownedStore);

        return ModifyStoreResponseDto.of(ownedStore);
    }

    public ReviewsFilterResponseDto getStoreReviews(
            AuthenticationUser user,
            StoreReviewsSearchCondition searchCondition,
            Long storeId, Integer pageNum
    ) {

        userAdapter.searchQueryUserByEmailAndStatus(user.getUsername());
        Store findStore = storeAdapter.searchQueryStoreById(storeId);
        Pageable pageable = PageUtil
                .createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, Boolean.FALSE);

        log.info("asdfasdf");

        Page<ReviewsFilterDao> reviewsFilterDaoList = userReviewsAdapter
                .searchQueryReviewsFilterPage(searchCondition, findStore, pageable);

        List<ReviewsFilterDao> content = reviewsFilterDaoList.getContent();
        Long orderId = content.get(0).getOrderId();
        List<Menu> menuList = menuAdapter.searchQueryOrderItemByOrderId(orderId);

        return ReviewsFilterResponseDto.of(reviewsFilterDaoList, menuList);
    }
}



