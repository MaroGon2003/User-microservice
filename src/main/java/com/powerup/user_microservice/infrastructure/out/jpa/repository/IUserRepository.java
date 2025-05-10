package com.powerup.user_microservice.infrastructure.out.jpa.repository;

import com.powerup.user_microservice.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByDni(Integer dni);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmailOrDni(String email, Integer dni);

    Optional<Long> findIdByEmail(String email);
}
