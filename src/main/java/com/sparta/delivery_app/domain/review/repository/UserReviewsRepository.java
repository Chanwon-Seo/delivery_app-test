package com.sparta.delivery_app.domain.review.repository;

import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReviewsRepository extends JpaRepository<UserReviews, Long>,
        UserReviewsQueryRepository {

    Optional<Long> findManagerReviewIdById(Long reviewId);

    Page<UserReviews> findAllByReviewStatus(Pageable pageable, ReviewStatus reviewStatus);
}
