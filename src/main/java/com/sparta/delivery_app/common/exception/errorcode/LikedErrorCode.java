package com.sparta.delivery_app.common.exception.errorcode;

import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikedErrorCode implements ErrorCode {
    LIKED_ALREADY_REGISTERED_STORE(StatusCode.BAD_REQUEST.getCode(), "이미 등록된 관심 매장입니다."),
    LIKED_UNREGISTERED_STORE(StatusCode.BAD_REQUEST.getCode(), "등록되지 않은 관심 매장입니다."),
    LIKED_ALREADY_REGISTERED_USER_REVIEW(StatusCode.BAD_REQUEST.getCode(), "이미 좋아요를 누른 유저 댓글입니다"),
    LIKED_UNREGISTERED_USER_REVIEW(StatusCode.BAD_REQUEST.getCode(), "좋아요를 누르지 않은 유저 댓글입니다."),
    ;


    private final Integer httpStatusCode;
    private final String description;
}
