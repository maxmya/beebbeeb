package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    List<VendorEntity> findAllByActive(boolean active);
    int countAllByActive(boolean active);
}
