package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity,Long> {
}
