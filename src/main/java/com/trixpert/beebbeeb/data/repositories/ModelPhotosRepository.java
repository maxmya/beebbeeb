package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ModelPhotosRepository extends JpaRepository<ModelPhotosEntity,Long> {

}
