package com.powerup.user_microservice.infrastructure.out.jpa.adapter;

import com.powerup.user_microservice.domain.model.RolModel;
import com.powerup.user_microservice.domain.model.UserModel;
import com.powerup.user_microservice.domain.model.UserRolesModel;
import com.powerup.user_microservice.domain.spi.IUserPersistencePort;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IRolEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IUserRolesEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IRolRepository;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IUserRepository;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IUserRolesRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IRolRepository rolRepository;
    private final IUserRolesRepository userRolesRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRolEntityMapper rolEntityMapper;
    private final IUserRolesEntityMapper userRolesEntityMapper;

    @Override
    public UserModel saveUser(UserModel userModel) {

        return userEntityMapper.toUserModel(userRepository.save(userEntityMapper.toEntity(userModel)));

    }

    @Override
    public void saveUserRoles(UserRolesModel userRolesModel) {

        userRolesRepository.save(userRolesEntityMapper.toUserRolesEntity(userRolesModel));

    }

    @Override
    public RolModel getRolById(Long id) {
        return rolEntityMapper.toRolModel(rolRepository.findById(id).orElseThrow());
    }

    @Override
    public boolean existUserByEmailOrDni(String email, Integer dni) {
        return userRepository.existsByEmailOrDni(email, dni);
    }

}
