package com.powerup.user_microservice.domain.usecase;

import com.powerup.user_microservice.domain.api.IUserServicePort;
import com.powerup.user_microservice.domain.exception.UserAlreadyExistsException;
import com.powerup.user_microservice.domain.exception.UserUnderAgeException;
import com.powerup.user_microservice.domain.model.RoleEnum;
import com.powerup.user_microservice.domain.model.UserModel;
import com.powerup.user_microservice.domain.spi.IEncrypterPort;
import com.powerup.user_microservice.domain.spi.IRoleInterceptorPort;
import com.powerup.user_microservice.domain.spi.IUserPersistencePort;
import com.powerup.user_microservice.domain.utils.DomainConstants;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IEncrypterPort encryptPort;
    private final IRoleInterceptorPort roleInterceptorPort;



    public UserUseCase(IUserPersistencePort userPersistencePort, IEncrypterPort encryptPort, IRoleInterceptorPort roleInterceptorPort) {
        this.userPersistencePort = userPersistencePort;
        this.encryptPort = encryptPort;
        this.roleInterceptorPort = roleInterceptorPort;
    }

    @Override
    public void saveUser(UserModel userModel) {

        validateDni(userModel.getDni());
        validateEmail(userModel.getEmail());
        validatePhone(userModel.getPhone());

        if(userPersistencePort.existUserByEmailOrDni(userModel.getEmail(), userModel.getDni())){
            throw new UserAlreadyExistsException(DomainConstants.USER_ALREADY_EXISTS, userModel.getEmail(), userModel.getDni());
        }

        LocalDate birthDateLocal = userModel.getBirdDate();
        if(Period.between(birthDateLocal, LocalDate.now()).getYears() < DomainConstants.AGE_MIN){
            throw new UserUnderAgeException(DomainConstants.USER_UNDER_AGE);
        }

        userModel.setPassword(encryptPort.encryptPassword(userModel.getPassword()));

        if(roleInterceptorPort.jwtExists()){

            if(roleInterceptorPort.isAdmin()) {
                userModel.setRoleModel(userPersistencePort.getRolById(RoleEnum.ROLE_SELLER.getId()));
            }else{
                throw new IllegalArgumentException(DomainConstants.INVALID_USER_ROLE);
            }

        } else{
            userModel.setRoleModel(userPersistencePort.getRolById(RoleEnum.ROLE_BUYER.getId()));
        }

        userPersistencePort.saveUser(userModel);

    }

    @Override
    public Long getUserIdByEmail(String email) {
        if (email == null || !Pattern.matches(DomainConstants.EMAIL_REGEX, email)) {
            throw new IllegalArgumentException(DomainConstants.INVALID_EMAIL_MESSAGE);
        }

        UserModel user = userPersistencePort.getUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(DomainConstants.USER_NOT_FOUND));

        return user.getId();
    }

    private void validateDni(Integer dni) {
        if (dni == null || !dni.toString().matches(DomainConstants.DNI_REGEX)) {
            throw new IllegalArgumentException(DomainConstants.INVALID_DNI_MESSAGE);
        }
    }

    private void validateEmail(String email) {
        if (email == null || !Pattern.matches(DomainConstants.EMAIL_REGEX, email)) {
            throw new IllegalArgumentException(DomainConstants.INVALID_EMAIL_MESSAGE);
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || !Pattern.matches(DomainConstants.PHONE_REGEX, phone)) {
            throw new IllegalArgumentException(DomainConstants.INVALID_PHONE_MESSAGE);
        }
    }
}
