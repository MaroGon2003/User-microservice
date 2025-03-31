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
import java.util.regex.Pattern;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IEncrypterPort encryptPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IEncrypterPort encryptPort) {
        this.userPersistencePort = userPersistencePort;
        this.encryptPort = encryptPort;
    }

    @Override
    public void saveUser(UserModel userModel) {

        validateDni(userModel.getDni());
        validateEmail(userModel.getEmail());
        validatePhone(userModel.getPhone());

        if(userPersistencePort.existUserByEmailOrDni(userModel.getEmail(), userModel.getDni())){
            throw new UserAlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS, userModel.getEmail(), userModel.getDni());
        }

        LocalDate birthDateLocal = userModel.getBirdDate();
        if(Period.between(birthDateLocal, LocalDate.now()).getYears() < Constants.AGE_MIN){
            throw new UserUnderAgeException(ErrorMessages.USER_UNDER_AGE);
        }

        userModel.setPassword(encryptPort.encryptPassword(userModel.getPassword()));
        UserModel userSaved = userPersistencePort.saveUser(userModel);
        saveUserRole(userSaved, RoleEnum.ROLE_SELLER.getId());

    }

    private void saveUserRole(UserModel userModel, Long rolId) {

        RolModel rolModel = userPersistencePort.getRolById(rolId);

        if (userModel == null || rolModel == null) {
            throw new InvalidUserRoleException(ErrorMessages.INVALID_USER_ROLE);
        }

        UserRolesModel userRolesModel = new UserRolesModel(userModel, rolModel);

        userPersistencePort.saveUserRoles(userRolesModel);

    }

    private void validateDni(Integer dni) {
        if (dni == null || !dni.toString().matches(Constants.DNI_REGEX)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_DNI_MESSAGE);
        }
    }

    private void validateEmail(String email) {
        if (email == null || !Pattern.matches(Constants.EMAIL_REGEX, email)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_EMAIL_MESSAGE);
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || !Pattern.matches(Constants.PHONE_REGEX, phone)) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_PHONE_MESSAGE);
        }
    }
}
