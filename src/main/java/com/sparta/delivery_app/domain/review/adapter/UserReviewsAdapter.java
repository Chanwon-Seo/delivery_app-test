package com.sparta.delivery_app.domain.review.adapter;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.ReviewNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.ReviewStatusException;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.UserReviewsRepository;
import com.sparta.delivery_app.domain.review.repository.dao.ReviewsFilterDao;
import com.sparta.delivery_app.domain.review.repository.dao.UserReviewsWithManagerReviewsDao;
import com.sparta.delivery_app.domain.store.dto.request.StoreReviewsSearchCondition;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReviewsAdapter {

    private final UserReviewsRepository userReviewsRepository;

    /**
     * QueryDsl
     */
    public boolean existsQueryUserReviewById(Long reviewId) {
        return userReviewsRepository.existsQueryByUserReviewId(reviewId);
    }

    public UserReviews searchQueryUserReviewById(Long userReviewId) {
        return userReviewsRepository.searchQueryUserReviewById(userReviewId).orElseThrow(() ->
                new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW));
    }

    public List<UserReviewsWithManagerReviewsDao> searchQueryUserReviews(User user) {
        return userReviewsRepository.searchQueryUserReviewByUser(user);
    }

    public Page<ReviewsFilterDao> searchQueryReviewsFilterPage(
            StoreReviewsSearchCondition storeReviewsDto, Store findStore, Pageable pageable) {
        return userReviewsRepository.searchQueryReviewsFilterPage(storeReviewsDto, findStore,
                pageable);
    }

    /**
     * 리뷰 등록
     */
    public void saveReview(UserReviews userReviews) {
        userReviewsRepository.save(userReviews);
    }

    /**
     * 메뉴 id, 상태 검증
     */
    public UserReviews checkValidReviewByIdAndReviewStatus(Long reviewId) {
        UserReviews userReviews = findById(reviewId);

        if (userReviews.getReviewStatus().equals(ReviewStatus.DISABLE)) {
            throw new ReviewStatusException(ReviewErrorCode.DELETED_REVIEW);
        }

        return userReviews;
    }

    /**
     * 리뷰 Id 검증
     */
    private UserReviews findById(Long reviewId) {
        return userReviewsRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW));
    }

    /**
     * 이미지 업로드 중 문제 발생 시 임시 등록한 리뷰 삭제
     */
    public void deleteTempReview(UserReviews tempReview) {
        userReviewsRepository.delete(tempReview);
    }


}
