package com.powerup.user_microservice.infrastructure.out.jpa.repository;

import com.powerup.user_microservice.infrastructure.out.jpa.entity.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRolesRepository extends JpaRepository<UserRolesEntity, Long> {
    Optional<UserRolesEntity> findByUserEmail(String email);
}
