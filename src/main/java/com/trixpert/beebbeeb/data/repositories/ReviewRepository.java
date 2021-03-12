package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {
}
