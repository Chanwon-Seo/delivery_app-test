package com.sparta.delivery_app.domain.liked.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeCreateEntity;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreLiked extends BaseTimeCreateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liked_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public StoreLiked(Store store, User user) {
        this.store = store;
        this.user = user;
    }

    public static StoreLiked saveStoreLiked(Store store, User user) {
        return StoreLiked.builder()
                .store(store)
                .user(user)
                .build();
    }
}
