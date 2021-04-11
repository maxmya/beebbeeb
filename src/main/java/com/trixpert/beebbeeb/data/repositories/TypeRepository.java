package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {

    List<TypeEntity> findAllByActive(Boolean isActive);

    Optional<TypeEntity> findAllByName(String typeName);
}
