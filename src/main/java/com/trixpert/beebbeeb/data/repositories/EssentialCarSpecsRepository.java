package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.entites.EssentialSpecsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EssentialCarSpecsRepository extends JpaRepository<EssentialSpecsEntity, Long> {
    List<EssentialSpecsEntity> findAllByCarAndActive(CarEntity carEntity, boolean active);
}
