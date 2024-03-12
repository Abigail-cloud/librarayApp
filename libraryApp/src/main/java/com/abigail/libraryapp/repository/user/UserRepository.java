package com.abigail.libraryapp.repository.user;

import com.abigail.libraryapp.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail (String email);
    Optional<UserEntity>findByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
}
