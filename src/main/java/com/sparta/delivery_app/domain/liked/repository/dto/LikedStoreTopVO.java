package com.sparta.delivery_app.domain.liked.repository.dto;

public record LikedStoreTopVO(
        String storeName,
        String storeInfo,
        Long minTotalPrice,
        String storeAddress,
        Long likedStoreNum,
        Integer menuSize,
        Long orderCount
) {
    public LikedStoreTopVO(
            String storeName, String storeInfo, Long minTotalPrice,
            String storeAddress, Long likedStoreNum, Integer menuSize,
            Long orderCount) {
        this.storeName = storeName;
        this.storeInfo = storeInfo;
        this.minTotalPrice = minTotalPrice;
        this.storeAddress = storeAddress;
        this.likedStoreNum = likedStoreNum;
        this.menuSize = menuSize;
        this.orderCount = orderCount;
    }
}
