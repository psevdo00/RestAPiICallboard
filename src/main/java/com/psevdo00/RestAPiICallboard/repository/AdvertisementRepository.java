package com.psevdo00.RestAPiICallboard.repository;

import com.psevdo00.RestAPiICallboard.entity.AdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long>, JpaSpecificationExecutor<AdvertisementEntity> {

    List<AdvertisementEntity> findAll();

    @Query("select a from AdvertisementEntity a where a.title like CONCAT('%', :title, '%')")
    List<AdvertisementEntity> findByTitle(@Param("title") String title);

    @Query("select a from AdvertisementEntity a where a.category.id = :id or (a.category.parent is not null and a.category.parent.id = :id)")
    List<AdvertisementEntity> findByCategory(@Param("id") Long id);

}
