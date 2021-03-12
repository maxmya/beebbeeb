package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity,Long> {
}
