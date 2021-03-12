package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Long> {
}
