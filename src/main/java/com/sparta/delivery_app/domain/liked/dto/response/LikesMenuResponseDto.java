package com.sparta.delivery_app.domain.liked.dto.response;

import com.sparta.delivery_app.domain.liked.repository.dto.LikedMenuWithUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class LikesMenuResponseDto {
    private Boolean nextPageYN;
    private Long totalElements;
    private Integer totalPages;
    private List<LikedMenuWithUserDto> likesStoreDtoList;

    public static LikesMenuResponseDto of(Page<LikedMenuWithUserDto> dto) {
        return LikesMenuResponseDto.builder()
                .nextPageYN(dto.hasNext())
                .totalElements(dto.getTotalElements())
                .totalPages(dto.getTotalPages())
                .likesStoreDtoList(dto.getContent())
                .build();
    }
}