package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.SalesManEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesManRepository extends JpaRepository<SalesManEntity,Long> {
}
