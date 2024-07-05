package com.sparta.delivery_app.domain.user.repository;

import static com.sparta.delivery_app.domain.user.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.user.entity.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<User> searchQueryByEmail(String email) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(user)
                        .where(user.email.eq(email))
                        .fetchOne());
    }
}
