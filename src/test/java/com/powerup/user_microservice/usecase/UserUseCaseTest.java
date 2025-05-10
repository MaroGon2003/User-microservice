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
import com.powerup.user_microservice.domain.utils.DomainConstants;
import com.powerup.user_microservice.usecase.factory.UserTestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void When_ValidEmail_Expect_ReturnUserId() {
        // Arrange
        String email = "test@example.com";
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setEmail(email);

        when(userPersistencePort.getUserByEmail(email)).thenReturn(Optional.of(userModel));

        // Act
        Long userId = userUseCase.getUserIdByEmail(email);

        // Assert
        assertEquals(1L, userId);
    }

    @Test
    void When_InvalidEmailFormat_Expect_IllegalArgumentException() {
        // Arrange
        String invalidEmail = "invalid-email";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userUseCase.getUserIdByEmail(invalidEmail));
        assertEquals(DomainConstants.INVALID_EMAIL_MESSAGE, exception.getMessage());
    }

    @Test
    void When_NullEmail_Expect_IllegalArgumentException() {
        // Arrange
        String nullEmail = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userUseCase.getUserIdByEmail(nullEmail));
        assertEquals(DomainConstants.INVALID_EMAIL_MESSAGE, exception.getMessage());
    }

    @Test
    void When_UserNotFound_Expect_IllegalArgumentException() {
        // Arrange
        String email = "notfound@example.com";

        when(userPersistencePort.getUserByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userUseCase.getUserIdByEmail(email));
        assertEquals(DomainConstants.USER_NOT_FOUND, exception.getMessage());
    }

}
