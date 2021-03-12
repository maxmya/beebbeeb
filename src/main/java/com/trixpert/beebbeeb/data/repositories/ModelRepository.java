package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.BrandEntity;
import com.trixpert.beebbeeb.data.entites.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.jws.WebParam;
import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
    List<ModelEntity> findAllByActive(boolean isActive);
    List<ModelEntity> findAllByActiveAndBrand(boolean isActive , BrandEntity brand);
}
