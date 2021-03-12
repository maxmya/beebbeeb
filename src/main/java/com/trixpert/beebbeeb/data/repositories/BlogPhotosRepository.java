package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.BlogPhotosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPhotosRepository extends JpaRepository<BlogPhotosEntity, Long> {
}
