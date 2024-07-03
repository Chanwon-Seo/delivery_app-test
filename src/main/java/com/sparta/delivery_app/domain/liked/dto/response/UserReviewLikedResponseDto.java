package com.sparta.delivery_app.domain.liked.dto.response;

import com.sparta.delivery_app.domain.liked.repository.dto.UserReviewLikedVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserReviewLikedResponseDto {
    private Boolean nextPageYN;
    private Long totalElements;
    private Integer totalPages;
    private List<UserReviewLikedVO> likesStoreDtoList;

    public static UserReviewLikedResponseDto of(Page<UserReviewLikedVO> vo) {
        return UserReviewLikedResponseDto.builder()
                .nextPageYN(vo.hasNext())
                .totalElements(vo.getTotalElements())
                .totalPages(vo.getTotalPages())
                .likesStoreDtoList(vo.getContent())
                .build();
    }
}
