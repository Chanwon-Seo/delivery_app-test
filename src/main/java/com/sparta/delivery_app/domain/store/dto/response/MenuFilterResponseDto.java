package com.sparta.delivery_app.domain.store.dto.response;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuFilterResponseDto {

    private String menuName;
    private Long menuPrice;
    private String menuImagePath;

    public static MenuFilterResponseDto of(Menu menu) {
        return MenuFilterResponseDto.builder()
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .menuImagePath(menu.getMenuImagePath())
                .build();
    }

    public static List<MenuFilterResponseDto> fromMenuDto(List<Menu> menuList) {
        return menuList.stream()
                .map(MenuFilterResponseDto::of)
                .toList();
    }
}
