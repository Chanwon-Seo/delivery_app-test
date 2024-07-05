package com.sparta.delivery_app.domain.store.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreReviewsRequestDto {

    private Boolean hasPhoto = Boolean.FALSE;
    private Boolean isCreated = Boolean.FALSE;
    private Boolean hasRating = Boolean.FALSE;
    private Integer pageNum = 1;

}
