package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.BrandEntity;
import com.trixpert.beebbeeb.data.entites.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByActive(Boolean isActive);
}
