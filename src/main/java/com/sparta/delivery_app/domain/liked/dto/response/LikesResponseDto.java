package com.sparta.delivery_app.domain.liked.dto.response;

import com.sparta.delivery_app.domain.liked.repository.dto.LikedWithUserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class LikesResponseDto {
    private Boolean nextPageYN;
    private Long totalElements;
    private Integer totalPages;
    private List<LikedWithUserVO> likesStoreDtoList;

    public static LikesResponseDto of(Page<LikedWithUserVO> dto) {
        return LikesResponseDto.builder()
                .nextPageYN(dto.hasNext())
                .totalElements(dto.getTotalElements())
                .totalPages(dto.getTotalPages())
                .likesStoreDtoList(dto.getContent())
                .build();
    }
}
