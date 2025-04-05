package com.psevdo00.RestAPiICallboard.repository;

import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    Optional<UserEntity> findById(Long id);

    @Query("select password from UserEntity where username = :username")
    String findPasswordByUsername(@Param("username") String username);
}
