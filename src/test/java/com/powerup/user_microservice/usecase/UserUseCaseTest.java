package com.powerup.user_microservice.usecase;

import com.powerup.user_microservice.domain.exception.UserAlreadyExistsException;
import com.powerup.user_microservice.domain.exception.UserUnderAgeException;
import com.powerup.user_microservice.domain.model.RoleModel;
import com.powerup.user_microservice.domain.model.RoleEnum;
import com.powerup.user_microservice.domain.model.UserModel;
import com.powerup.user_microservice.domain.spi.IEncrypterPort;
import com.powerup.user_microservice.domain.spi.IRoleInterceptorPort;
import com.powerup.user_microservice.domain.spi.IUserPersistencePort;
import com.powerup.user_microservice.domain.usecase.UserUseCase;
import com.powerup.user_microservice.usecase.factory.UserTestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @InjectMocks
    private UserUseCase userUseCase;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IEncrypterPort encryptPort;

    @Mock
    private IRoleInterceptorPort roleInterceptorPort;

    @Test
    void When_SaveUserSeller_Expect_Success() {
        // Arrange
        UserModel userModel = UserTestDataFactory.createValidUser();

        when(userPersistencePort.existUserByEmailOrDni(userModel.getEmail(), userModel.getDni())).thenReturn(false);
        when(encryptPort.encryptPassword(userModel.getPassword())).thenReturn("encryptedPassword");
        when(userPersistencePort.getRolById(RoleEnum.ROLE_SELLER.getId())).thenReturn(new RoleModel(RoleEnum.ROLE_SELLER.getId(), RoleEnum.ROLE_SELLER, "Seller role"));
        when(roleInterceptorPort.jwtExists()).thenReturn(true);
        when(roleInterceptorPort.isAdmin()).thenReturn(true);

        // Act
        userUseCase.saveUser(userModel);

        // Assert
        verify(userPersistencePort).existUserByEmailOrDni(userModel.getEmail(), userModel.getDni());
        verify(encryptPort).encryptPassword("password");
        verify(userPersistencePort).saveUser(userModel);
        verify(userPersistencePort).getRolById(RoleEnum.ROLE_SELLER.getId());
    }

    @Test
    void When_SaveUserSeller_Expect_UserAlreadyExistsException() {
        // Arrange
        UserModel userModel = UserTestDataFactory.createValidUser();

        when(userPersistencePort.existUserByEmailOrDni(userModel.getEmail(), userModel.getDni())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void When_SaveUserWithInvalidEmail_Expect_IllegalArgumentException() {
        // Arrange
        UserModel userModel = UserTestDataFactory.createUserWithInvalidEmail();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void When_SaveUserWithInvalidPhone_Expect_IllegalArgumentException() {
        // Arrange
        UserModel userModel = UserTestDataFactory.createUserWithInvalidPhone();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void When_SaveUserWithInvalidDni_Expect_IllegalArgumentException() {
        // Arrange
        UserModel userModel = UserTestDataFactory.createUserWithInvalidDni();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void When_SaveUserUnderAge_Expect_UserUnderAgeException() {
        // Arrange
        UserModel userModel = UserTestDataFactory.createUnderageUser();

        // Act & Assert
        assertThrows(UserUnderAgeException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void When_SaveUserWithInvalidDniFormat_Expect_IllegalArgumentException() {
        // Arrange
        UserModel userModel = UserTestDataFactory.createValidUser();
        userModel.setDni(-12312); // Assuming this does not match the DNI regex

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void When_SaveUserWithInvalidRole_Expect_IllegalArgumentException() {
        // Arrange
        UserModel userModel = UserTestDataFactory.createValidUser();
        when(roleInterceptorPort.jwtExists()).thenReturn(true);
        when(roleInterceptorPort.isAdmin()).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void When_UserBuyer_Expect_Success() {
        // Arrange
        UserModel userModel = UserTestDataFactory.createValidUser();
        when(userPersistencePort.existUserByEmailOrDni(userModel.getEmail(), userModel.getDni())).thenReturn(false);
        when(encryptPort.encryptPassword(userModel.getPassword())).thenReturn("encryptedPassword");
        when(userPersistencePort.getRolById(RoleEnum.ROLE_BUYER.getId())).thenReturn(new RoleModel(RoleEnum.ROLE_BUYER.getId(), RoleEnum.ROLE_BUYER, "Buyer role"));
        when(roleInterceptorPort.jwtExists()).thenReturn(false);

        // Act
        userUseCase.saveUser(userModel);

        // Assert
        verify(userPersistencePort).existUserByEmailOrDni(userModel.getEmail(), userModel.getDni());
        verify(encryptPort).encryptPassword("password");
        verify(userPersistencePort).saveUser(userModel);
        verify(userPersistencePort).getRolById(RoleEnum.ROLE_BUYER.getId());
    }

}
