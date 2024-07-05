package com.sparta.delivery_app.domain.store.dto.response;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.review.repository.dao.ReviewsFilterDao;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class ReviewsFilterResponseDto {

    private Boolean nextPageYN;
    private Long totalElements;
    private Integer totalPages;
    private List<ReviewResponseDto> reviewDtoList;

    public static ReviewsFilterResponseDto of(
            Page<ReviewsFilterDao> daoPage,
            List<Menu> menuList
    ) {
        return ReviewsFilterResponseDto.builder()
                .nextPageYN(daoPage.hasNext())
                .totalElements(daoPage.getTotalElements())
                .totalPages(daoPage.getTotalPages())
                .reviewDtoList(
                        ReviewResponseDto.fromReviewDto(menuList, daoPage.getContent().stream()
                                .flatMap(dao -> dao.getReviewDaoList().stream())
                                .collect(Collectors.toList())))
                .build();
    }
}
