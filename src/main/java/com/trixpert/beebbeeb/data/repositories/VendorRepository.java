package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    List<VendorEntity> findAllByActive(boolean active);

    Optional<VendorEntity> findAllByManager(UserEntity user);

    int countAllByActive(boolean active);
}
