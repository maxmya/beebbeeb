package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.HomeTelephoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeTelephoneRepository extends JpaRepository<HomeTelephoneEntity, Long> {
}
