package com.sparta.delivery_app.domain.review.repository.dao;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewsFilterDao {

    private Long orderId;
    private String storeName;
    private List<ReviewDao> reviewDaoList;

    public ReviewsFilterDao(Long orderId, String storeName,
            List<ReviewDao> reviewDaoList) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.reviewDaoList = reviewDaoList;
    }
}
