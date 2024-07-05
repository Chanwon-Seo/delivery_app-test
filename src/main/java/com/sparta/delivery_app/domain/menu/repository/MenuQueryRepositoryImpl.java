package com.sparta.delivery_app.domain.menu.repository;

import static com.sparta.delivery_app.domain.menu.entity.QMenu.menu;
import static com.sparta.delivery_app.domain.order.entity.QOrder.order;
import static com.sparta.delivery_app.domain.order.entity.QOrderItem.orderItem;
import static com.sparta.delivery_app.domain.store.entity.QStore.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MenuQueryRepositoryImpl implements MenuQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Menu> searchQueryOrderItemByOrderId(Long orderId) {
        return jpaQueryFactory
                .select(menu)
                .from(order)
                .join(store).on(order.store.id.eq(store.id))
                .join(orderItem).on(order.id.eq(orderItem.order.id))
                .join(menu).on(menu.store.id.eq(store.id))
                .where(order.id.eq(orderId))
                .fetch();

    }
}
