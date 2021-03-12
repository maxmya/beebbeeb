package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.BankEntity;
import com.trixpert.beebbeeb.data.to.BankDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Long> {
    public List<BankEntity> findAllByActive(boolean active);
}
