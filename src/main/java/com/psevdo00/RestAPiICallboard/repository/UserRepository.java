package com.psevdo00.RestAPiICallboard.repository;

import com.psevdo00.RestAPiICallboard.dto.response.UserSessionDTO;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    @NonNull
    Optional<UserEntity> findById(Long id);

    @Query("SELECT NEW com.psevdo00.RestAPiICallboard.dto.response.UserSessionDTO(u.id, :username, u.role) FROM UserEntity u WHERE u.username = :username")
    UserSessionDTO valuesForUserSessionDTO(@Param("username") String username);

}
