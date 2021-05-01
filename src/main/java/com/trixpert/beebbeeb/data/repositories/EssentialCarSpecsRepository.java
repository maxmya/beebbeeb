package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.entites.EssentialSpecsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EssentialCarSpecsRepository extends JpaRepository<EssentialSpecsEntity, Long> {
    List<EssentialSpecsEntity> findAllByCarAndActive(CarEntity carEntity, boolean active);
}
