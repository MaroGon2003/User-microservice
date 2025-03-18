package com.powerup.user_microservice.infrastructure.out.jpa.mapper;

import com.powerup.user_microservice.domain.model.UserModel;
import com.powerup.user_microservice.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {

    UserEntity toEntity(UserModel userModel);

    UserModel toUserModel(UserEntity userEntity);

}
