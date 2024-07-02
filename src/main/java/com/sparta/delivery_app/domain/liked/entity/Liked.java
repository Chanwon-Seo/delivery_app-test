package com.sparta.delivery_app.domain.liked.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeCreateEntity;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Liked extends BaseTimeCreateEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Liked(User user) {
        this.user = user;
    }
}
