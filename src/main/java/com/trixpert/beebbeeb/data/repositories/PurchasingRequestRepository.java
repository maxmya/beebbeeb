package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.PurchasingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasingRequestRepository extends JpaRepository<PurchasingRequestEntity , Long> {
    public List<PurchasingRequestEntity> findAllByActive(boolean active);
}
