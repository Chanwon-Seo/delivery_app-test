package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.dao.ReviewsFilterDao;
import com.sparta.delivery_app.domain.review.repository.dao.UserReviewsWithManagerReviewsDao;
import com.sparta.delivery_app.domain.store.dto.request.StoreReviewsSearchCondition;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserReviewsQueryRepository {

    boolean existsQueryByUserReviewId(Long userReviewId);

    Optional<UserReviews> searchQueryUserReviewById(Long userReviewId);

    List<UserReviewsWithManagerReviewsDao> searchQueryUserReviewByUser(User user);

    Page<ReviewsFilterDao> searchQueryReviewsFilterPage(
            StoreReviewsSearchCondition storeReviewsDto,
            Store findStore, Pageable pageable);
}
