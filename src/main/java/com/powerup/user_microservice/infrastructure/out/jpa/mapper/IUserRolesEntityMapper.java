package com.powerup.user_microservice.infrastructure.out.jpa.mapper;

import com.powerup.user_microservice.domain.model.UserRolesModel;
import com.powerup.user_microservice.infrastructure.out.jpa.entity.UserRolesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRolesEntityMapper {

    @Mapping(target = "role", source = "rolModel")
    @Mapping(target = "user", source = "userModel")
    @Mapping(target = "userRolesId" , expression = "java(new UserRolesEntity.UserRolesId(userRolesModel.getUserModel().getId(), userRolesModel.getRolModel().getId()))")
    UserRolesEntity toUserRolesEntity(UserRolesModel userRolesModel);

}
