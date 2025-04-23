package com.psevdo00.RestAPiICallboard.repository;

import com.psevdo00.RestAPiICallboard.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Override
    List<CategoryEntity> findAll();

    String findNameById(Long id);

    Optional<CategoryEntity> findById(Long id);


}
