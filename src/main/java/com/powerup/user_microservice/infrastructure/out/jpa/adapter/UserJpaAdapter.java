package com.powerup.user_microservice.infrastructure.out.jpa.adapter;

import com.powerup.user_microservice.domain.model.RoleModel;
import com.powerup.user_microservice.domain.model.UserModel;
import com.powerup.user_microservice.domain.spi.IUserPersistencePort;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IRolEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IRolRepository;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IRolRepository rolRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRolEntityMapper rolEntityMapper;

    @Override
    public void saveUser(UserModel userModel) {

        userEntityMapper.toUserModel(userRepository.save(userEntityMapper.toEntity(userModel)));

    }

    @Override
    public RoleModel getRolById(Long id) {
        return rolEntityMapper.toRolModel(rolRepository.findById(id).orElseThrow());
    }

    @Override
    public boolean existUserByEmailOrDni(String email, Integer dni) {
        return userRepository.existsByEmailOrDni(email, dni);
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUserModel);
    }
}
