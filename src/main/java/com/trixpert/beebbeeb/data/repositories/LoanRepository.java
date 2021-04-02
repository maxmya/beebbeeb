package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.entites.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LoanRepository extends JpaRepository<LoanEntity,Long> {
    List<LoanEntity> findAllByActive(boolean active);

    List<LoanEntity> findByCustomer(CustomerEntity customerEntity);
}
