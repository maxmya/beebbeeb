package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.entites.ExtraSpecsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraCarSpecsRepository extends JpaRepository<ExtraSpecsEntity, Long> {
    List<ExtraSpecsEntity> findAllByCarAndActive(CarEntity carEntity, boolean active);

}
