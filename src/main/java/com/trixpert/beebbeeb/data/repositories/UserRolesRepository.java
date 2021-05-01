package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    List<UserRoles> findAllByRoleId(long roleId);
}
