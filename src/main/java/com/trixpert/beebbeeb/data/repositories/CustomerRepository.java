package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByUser(UserEntity userEntity);

    List<CustomerEntity> findAllByActive(boolean isActive);

    int countAllByActive(boolean active);
}
