package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
}
