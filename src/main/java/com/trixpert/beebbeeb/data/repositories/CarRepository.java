package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.BrandEntity;
import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.entites.ModelEntity;
import com.trixpert.beebbeeb.data.to.CarDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,Long> {
    List<CarEntity> findAllByActive(boolean active);

    List<CarEntity> findAllByBrandAndActive(BrandEntity brand,boolean active);

    List<CarEntity> findAllByModelAndActive(ModelEntity model, boolean active);

}
