package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhone(String phone);

    Optional<UserEntity> findByEmail(String email);

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);

    List<UserEntity> findAllByIdIn(List<Long> ids);

}