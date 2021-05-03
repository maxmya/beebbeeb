package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.ReviewEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    ReviewEntity findByCarInstanceAndReviewer(CarInstanceEntity carInstanceEntity, UserEntity reviewer);
    ReviewEntity findByVendorAndReviewer(VendorEntity vendorEntity, UserEntity reviewer);
}
