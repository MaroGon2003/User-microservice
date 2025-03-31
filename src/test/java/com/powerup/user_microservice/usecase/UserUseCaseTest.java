package com.powerup.user_microservice.usecase;

import com.powerup.user_microservice.domain.exception.UserAlreadyExistsException;
import com.powerup.user_microservice.domain.model.RolModel;
import com.powerup.user_microservice.domain.model.RoleEnum;
import com.powerup.user_microservice.domain.model.UserModel;
import com.powerup.user_microservice.domain.model.UserRolesModel;
import com.powerup.user_microservice.domain.spi.IEncrypterPort;
import com.powerup.user_microservice.domain.spi.IUserPersistencePort;
import com.powerup.user_microservice.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void When_SaveUserSeller_Expect_Success() {
        // Arrange
        UserModel userModel = new UserModel();
        userModel.setEmail("test@example.com");
        userModel.setDni(12345678);
        userModel.setBirdDate(LocalDate.of(2000, 1, 1));
        userModel.setPassword("password");
        userModel.setPhone("123456789");

        when(userPersistencePort.existUserByEmailOrDni(userModel.getEmail(), userModel.getDni())).thenReturn(false);
        when(encryptPort.encryptPassword("password")).thenReturn("encryptedPassword");
        when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(userModel);
        when(userPersistencePort.getRolById(RoleEnum.ROLE_SELLER.getId())).thenReturn(new RolModel(RoleEnum.ROLE_SELLER.getId(), RoleEnum.ROLE_SELLER, "Seller role"));

        // Act
        userUseCase.saveUser(userModel);

        // Assert
        verify(userPersistencePort).existUserByEmailOrDni(userModel.getEmail(), userModel.getDni());
        verify(encryptPort).encryptPassword("password");
        verify(userPersistencePort).saveUser(userModel);
        verify(userPersistencePort).getRolById(RoleEnum.ROLE_SELLER.getId());
        verify(userPersistencePort).saveUserRoles(any(UserRolesModel.class));
    }

    @Test
    void When_SaveUserSeller_Expect_UserAlreadyExistsException() {
        // Arrange
        UserModel userModel = new UserModel();
        userModel.setEmail("test@example.com");
        userModel.setDni(12345678);
        userModel.setBirdDate(LocalDate.of(2000, 1, 1));
        userModel.setPassword("password");

        when(userPersistencePort.existUserByEmailOrDni(userModel.getEmail(), userModel.getDni())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userUseCase.saveUser(userModel));
    }

}
