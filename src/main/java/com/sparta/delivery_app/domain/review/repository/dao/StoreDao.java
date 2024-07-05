package com.sparta.delivery_app.domain.review.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class StoreDao {

    private Long orderId;
    private String storeName;

    public static StoreDao of(Long orderId, String storeName) {
        return StoreDao.builder()
                .orderId(orderId)
                .storeName(storeName)
                .build();
    }

}
