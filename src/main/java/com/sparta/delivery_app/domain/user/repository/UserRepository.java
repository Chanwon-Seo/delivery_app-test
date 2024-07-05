package com.sparta.delivery_app.domain.user.repository;

import com.sparta.delivery_app.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    Page<User> findAll(Pageable pageable);
}
