package com.sparta.delivery_app.domain.review.dto.response;

import com.sparta.delivery_app.domain.review.repository.dao.UserReviewsWithManagerReviewsDao;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReviewsAndManagerReviewsResponseDto {

    private UserReviewResponseDto userReviews;
    private ManagerReviewResponseDto managerReviews;

    public static UserReviewsAndManagerReviewsResponseDto of(
            UserReviewsWithManagerReviewsDao daoList) {
        UserReviewResponseDto userReviewDto = UserReviewResponseDto.of(
                daoList.userReviewId(),
                daoList.userReviewImagePath(),
                daoList.userReviewContent(),
                daoList.createdAt()
        );

        ManagerReviewResponseDto managerReviewDto = ManagerReviewResponseDto.of(
                daoList.storeName(), daoList.managerReviewContent()
        );

        return UserReviewsAndManagerReviewsResponseDto.builder()
                .userReviews(userReviewDto)
                .managerReviews(managerReviewDto)
                .build();
    }
}
