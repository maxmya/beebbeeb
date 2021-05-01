package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarInstanceRepository extends JpaRepository<CarInstanceEntity, Long> {
    List<CarInstanceEntity> findAllByActive(boolean active);

    Page<CarInstanceEntity> findAllByActive(boolean active, Pageable pageable);

    List<CarInstanceEntity> findAllByVendorAndActive(VendorEntity vendor, boolean active);

    int countAllByActive(boolean active);

}
