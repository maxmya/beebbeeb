package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {

}
