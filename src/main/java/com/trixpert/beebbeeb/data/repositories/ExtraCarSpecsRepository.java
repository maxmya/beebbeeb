package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.entites.EssentialSpecsEntity;
import com.trixpert.beebbeeb.data.entites.ExtraSpecsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtraCarSpecsRepository extends JpaRepository<ExtraSpecsEntity, Long> {
    List<ExtraSpecsEntity> findAllByCarAndActive(CarEntity carEntity, boolean active);

}
