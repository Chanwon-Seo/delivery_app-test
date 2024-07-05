package com.sparta.delivery_app.domain.store.dto.response;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.review.repository.dao.ReviewDao;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {

    private String nickName;
    private Long userReviewId;
    private String userReviewContent;
    private String userReviewImagePath;
    private LocalDateTime userReviewCreateAt;
    private Integer rating;
    private String managerReviewContent;
    private List<MenuFilterResponseDto> menuDtoList;

    public static ReviewResponseDto of(ReviewDao dao, List<Menu> menuList) {
        return ReviewResponseDto.builder()
                .nickName(dao.getUserNickName())
                .userReviewId(dao.getUserReviewId())
                .userReviewContent(dao.getUserReviewContent())
                .userReviewImagePath(dao.getUserReviewImagePath())
                .userReviewCreateAt(dao.getUserReviewCreateAt())
                .rating(dao.getRating())
                .menuDtoList(MenuFilterResponseDto.fromMenuDto(menuList))
                .build();
    }

    public static List<ReviewResponseDto> fromReviewDto(
            List<Menu> menuList,
            List<ReviewDao> reviewDaoList
    ) {
        return reviewDaoList.stream()
                .map(dao -> ReviewResponseDto.of(dao, menuList)).toList();
    }
}
