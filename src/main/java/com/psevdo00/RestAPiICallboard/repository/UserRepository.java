package com.psevdo00.RestAPiICallboard.repository;

import com.psevdo00.RestAPiICallboard.dto.response.UserDTO;
import com.psevdo00.RestAPiICallboard.dto.response.UserSessionDTO;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    @NonNull
    Optional<UserEntity> findById(Long id);

}
