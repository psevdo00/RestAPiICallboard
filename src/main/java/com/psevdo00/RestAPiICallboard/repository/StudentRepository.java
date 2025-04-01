package com.psevdo00.RestAPiICallboard.repository;

import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    @Query("select password from UserEntity where email = :email")
    String findPasswordByEmail(@Param("email") String email);
}
