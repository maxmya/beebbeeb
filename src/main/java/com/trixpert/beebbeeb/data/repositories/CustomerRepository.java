package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    List<CustomerEntity> findAllByActive(boolean isActive);
    int countAllByActive(boolean active);
}
