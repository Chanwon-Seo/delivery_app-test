package com.sparta.delivery_app.domain.liked.repository.dto;

public record MenuVO(
        Long menuId,
        String menuName,
        String menuInfo,
        Long menuPrice,
        String menuImagePath
        ) {
    public MenuVO(Long menuId, String menuName, String menuInfo, Long menuPrice, String menuImagePath) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuInfo = menuInfo;
        this.menuPrice = menuPrice;
        this.menuImagePath = menuImagePath;
    }
}
