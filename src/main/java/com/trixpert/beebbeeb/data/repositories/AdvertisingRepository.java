package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.AdvertisingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisingRepository extends JpaRepository<AdvertisingEntity, Long> {
}
