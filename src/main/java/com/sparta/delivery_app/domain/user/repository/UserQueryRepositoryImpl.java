package com.sparta.delivery_app.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.delivery_app.domain.user.entity.QUser.user;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> searchQueryByEmail(String email) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(user)
                        .where(user.email.eq(email))
                        .fetchOne());
    }
}
