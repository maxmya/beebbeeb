package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.BannerEntity;
import com.trixpert.beebbeeb.data.to.BannerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
   public List<BannerEntity> findAllByActive(boolean isActive);
}
