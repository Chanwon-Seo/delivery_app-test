package com.sparta.delivery_app.domain.review.repository.dao;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDao {

    private String userNickName;
    private Long userReviewId;
    private String userReviewContent;
    private String userReviewImagePath;
    private LocalDateTime userReviewCreateAt;
    private Integer rating;
    private String managerReviewContent;

    public ReviewDao(String userNickName, Long userReviewId, String userReviewContent,
            String userReviewImagePath,
            LocalDateTime userReviewCreateAt, Integer rating, String managerReviewContent) {
        this.userNickName = userNickName;
        this.userReviewId = userReviewId;
        this.userReviewContent = userReviewContent;
        this.userReviewImagePath = userReviewImagePath;
        this.userReviewCreateAt = userReviewCreateAt;
        this.rating = rating;
        this.managerReviewContent = managerReviewContent;
    }
}