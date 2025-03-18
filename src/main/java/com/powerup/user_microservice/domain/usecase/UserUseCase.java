package com.powerup.user_microservice.domain.usecase;

import com.powerup.user_microservice.domain.api.IUserServicePort;
import com.powerup.user_microservice.domain.exception.InvalidUserRoleException;
import com.powerup.user_microservice.domain.exception.UserAlreadyExistsException;
import com.powerup.user_microservice.domain.exception.UserUnderAgeException;
import com.powerup.user_microservice.domain.model.RolModel;
import com.powerup.user_microservice.domain.model.RoleEnum;
import com.powerup.user_microservice.domain.model.UserModel;
import com.powerup.user_microservice.domain.model.UserRolesModel;
import com.powerup.user_microservice.domain.spi.IEncrypterPort;
import com.powerup.user_microservice.domain.spi.IUserPersistencePort;
import com.powerup.user_microservice.domain.utils.Constants;
import com.powerup.user_microservice.domain.utils.ErrorMessages;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IEncrypterPort encryptPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IEncrypterPort encryptPort) {
        this.userPersistencePort = userPersistencePort;
        this.encryptPort = encryptPort;
    }

    @Override
    public void saveUserSeller(UserModel userModel) {

        if(userPersistencePort.existUserByEmailOrDni(userModel.getEmail(), userModel.getDni())){
            throw new UserAlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS);
        }

        LocalDate birthDateLocal = userModel.getBirdDate();
        if(Period.between(birthDateLocal, LocalDate.now()).getYears() < Constants.AGE_MIN){
            throw new UserUnderAgeException(ErrorMessages.USER_UNDER_AGE);
        }

        userModel.setPassword(encryptPort.encryptPassword(userModel.getPassword()));
        UserModel userSaved = userPersistencePort.saveUser(userModel);
        saveUserRole(userSaved, RoleEnum.SELLER.getId());

    }

    private void saveUserRole(UserModel userModel, Long rolId) {

        RolModel rolModel = userPersistencePort.getRolById(rolId);

        if (userModel == null || rolModel.getId() == null) {
            throw new InvalidUserRoleException(ErrorMessages.INVALID_USER_ROLE);
        }

        UserRolesModel userRolesModel = new UserRolesModel(userModel, rolModel);

        userPersistencePort.saveUserRoles(userRolesModel);

    }

}
