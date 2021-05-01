package com.trixpert.beebbeeb.data.repositories;

import com.trixpert.beebbeeb.data.entites.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
