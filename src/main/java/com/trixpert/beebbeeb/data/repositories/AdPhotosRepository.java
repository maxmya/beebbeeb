package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.AdPhotosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdPhotosRepository extends JpaRepository<AdPhotosEntity, Long> {
}
