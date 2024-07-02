package com.sparta.delivery_app.domain.liked.repository.dto;

import com.sparta.delivery_app.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class LikedMenuWithUserDto {
    private Long storeId;
    private String storeName;
    private String storeAddress;
    private List<MenuVO> menuList;

    public static LikedMenuWithUserDto of(Store store, List<MenuVO> menuList) {
        return LikedMenuWithUserDto.builder()
                .storeId(store.getId())
                .storeName(store.getStoreName())
                .storeAddress(store.getStoreAddress())
                .menuList(menuList)
                .build();
    }
}
