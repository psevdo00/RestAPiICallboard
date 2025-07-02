package com.psevdo00.RestAPiICallboard.repository;

import com.psevdo00.RestAPiICallboard.entity.AdvtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdvtRepository extends JpaRepository<AdvtEntity, Long>, JpaSpecificationExecutor<AdvtEntity> {

    List<AdvtEntity> findAll();

    @Query("select a from AdvtEntity a where a.title like CONCAT('%', :title, '%')")
    List<AdvtEntity> findByTitle(@Param("title") String title);

    @Query("select a from AdvtEntity a where a.category.id = :id or (a.category.parent is not null and a.category.parent.id = :id)")
    List<AdvtEntity> findByCategory(@Param("id") Long id);

}
