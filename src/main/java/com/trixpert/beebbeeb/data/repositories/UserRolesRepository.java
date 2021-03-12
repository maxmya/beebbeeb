package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    List<UserRoles> findAllByRoleId(long roleId);
}
