package com.powerup.user_microservice.application.mapper;

import com.powerup.user_microservice.application.dto.request.UserRequestDto;
import com.powerup.user_microservice.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {

    UserModel toUserModel(UserRequestDto userRequestDto);

}
