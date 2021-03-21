package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.PhotoEntity;
import com.trixpert.beebbeeb.data.to.PhotoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity,Long> {
    List<PhotoEntity> findAllByActive(boolean isActive);
}
