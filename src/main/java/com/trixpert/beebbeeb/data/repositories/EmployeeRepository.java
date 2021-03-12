package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.BranchEntity;
import com.trixpert.beebbeeb.data.entites.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findAllByBranchAndActive(BranchEntity branchEntity, boolean isActive);
}
