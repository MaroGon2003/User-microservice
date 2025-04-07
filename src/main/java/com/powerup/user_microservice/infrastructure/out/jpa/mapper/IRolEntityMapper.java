package com.powerup.user_microservice.infrastructure.out.jpa.mapper;

import com.powerup.user_microservice.domain.model.RoleModel;
import com.powerup.user_microservice.infrastructure.out.jpa.entity.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRolEntityMapper {

    RoleModel toRolModel(RolEntity rolEntity);

}
