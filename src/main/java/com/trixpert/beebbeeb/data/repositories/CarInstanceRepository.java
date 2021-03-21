package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarInstanceRepository extends JpaRepository<CarInstanceEntity, Long> {
    List<CarInstanceEntity> findAllByActive(boolean active);
    List<CarInstanceEntity> findAllByVendorAndActive(VendorEntity vendor, boolean active);
    int countAllByActive(boolean active);
}
