package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.BrandEntity;
import com.trixpert.beebbeeb.data.entites.ParentColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentColorRepository extends JpaRepository<ParentColorEntity, Long> {
    List<ParentColorEntity> findAllByActive(boolean isActive);

}
