package com.sparta.delivery_app.domain.user.repository;

import com.sparta.delivery_app.domain.user.entity.User;

import java.util.Optional;

public interface UserQueryRepository {
    Optional<User> searchQueryByEmail(String email);
}
