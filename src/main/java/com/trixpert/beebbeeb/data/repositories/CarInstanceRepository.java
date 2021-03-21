package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarInstanceRepository extends JpaRepository<CarInstanceEntity, Long> {
}
