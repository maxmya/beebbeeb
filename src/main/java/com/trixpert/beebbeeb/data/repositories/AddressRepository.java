package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.AddressEntity;
import com.trixpert.beebbeeb.data.entites.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository  extends JpaRepository<AddressEntity,Long> {
    public List<AddressEntity> findAllByActive(boolean active);


}
