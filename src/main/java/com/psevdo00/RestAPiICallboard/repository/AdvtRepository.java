package com.psevdo00.RestAPiICallboard.repository;

import com.psevdo00.RestAPiICallboard.entity.AdvtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvtRepository extends JpaRepository<AdvtEntity, Long> {

    List<AdvtEntity> findAll();

}
