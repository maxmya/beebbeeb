package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {
    Optional<RolesEntity> findByName(String roleName);
}
