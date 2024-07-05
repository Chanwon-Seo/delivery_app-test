package com.sparta.delivery_app.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewWithMenuResponseDto {

    private String menuName;
    private Long menuPrice;
    private String menuImagePath;

}
