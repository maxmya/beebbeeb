package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {

    List<TypeEntity> findAllByActive(Boolean isActive);
}
