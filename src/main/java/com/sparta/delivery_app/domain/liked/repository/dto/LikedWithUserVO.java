package com.sparta.delivery_app.domain.liked.repository.dto;

public record LikedWithUserVO(
        Long storeId,
        String storeName,
        String storeAddress
) {
    public LikedWithUserVO(Long storeId, String storeName, String storeAddress) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }
}
