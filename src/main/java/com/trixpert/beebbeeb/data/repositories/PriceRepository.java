package com.trixpert.beebbeeb.data.repositories;


import com.trixpert.beebbeeb.data.entites.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
}
