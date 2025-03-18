package com.powerup.user_microservice.infrastructure.out.jpa.repository;

import com.powerup.user_microservice.infrastructure.out.jpa.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolRepository extends JpaRepository<RolEntity, Long> {
}
