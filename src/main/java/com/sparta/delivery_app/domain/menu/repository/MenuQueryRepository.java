package com.sparta.delivery_app.domain.menu.repository;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import java.util.List;

public interface MenuQueryRepository {

    List<Menu> searchQueryOrderItemByOrderId(Long orderId);

}
