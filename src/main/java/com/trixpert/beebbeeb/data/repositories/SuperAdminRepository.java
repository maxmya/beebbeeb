package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.SuperAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdminEntity, Long> {
}
