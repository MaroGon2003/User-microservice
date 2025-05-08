package com.powerup.user_microservice.usecase;

import com.powerup.user_microservice.domain.exception.AccountLockedException;
import com.powerup.user_microservice.domain.exception.InvalidCredentialsException;
import com.powerup.user_microservice.domain.spi.ILoginAttemptPersistencePort;
import com.powerup.user_microservice.domain.spi.IUserValidationPort;
import com.powerup.user_microservice.domain.usecase.AuthUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @InjectMocks
    private AuthUseCase authUseCase;

    @Mock
    private IUserValidationPort validationPort;

    @Mock
    private ILoginAttemptPersistencePort loginAttemptPersistencePort;

    @Test
    void whenAccountIsBlocked_thenThrowAccountLockedException() {
        // Arrange
        String email = "blocked@example.com";
        when(loginAttemptPersistencePort.isBlocked(email)).thenReturn(true);

        // Act & Assert
        assertThrows(AccountLockedException.class, () -> authUseCase.login(email, "password"));
        verify(loginAttemptPersistencePort, never()).incrementAttempts(email);
    }

    @Test
    void whenValidCredentials_thenReturnToken() {
        // Arrange
        String email = "valid@example.com";
        String password = "validPassword";
        String token = "validToken";

        when(loginAttemptPersistencePort.isBlocked(email)).thenReturn(false);
        when(validationPort.validateUser(email, password)).thenReturn(token);

        // Act
        String result = authUseCase.login(email, password);

        // Assert
        verify(loginAttemptPersistencePort).resetAttempts(email);
        verify(validationPort).validateUser(email, password);
        verify(loginAttemptPersistencePort, never()).incrementAttempts(email);
        assert result.equals(token);
    }

    @Test
    void whenInvalidCredentials_thenThrowInvalidCredentialsException() {
        // Arrange
        String email = "invalid@example.com";
        String password = "invalidPassword";

        when(loginAttemptPersistencePort.isBlocked(email)).thenReturn(false);
        when(validationPort.validateUser(email, password)).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> authUseCase.login(email, password));
        verify(loginAttemptPersistencePort).incrementAttempts(email);
    }

    @Test
    void whenMaxAttemptsExceeded_thenThrowAccountLockedException() {
        // Arrange
        String email = "maxAttempts@example.com";
        String password = "invalidPassword";

        when(loginAttemptPersistencePort.isBlocked(email)).thenReturn(false);
        when(validationPort.validateUser(email, password)).thenThrow(new RuntimeException());
        when(loginAttemptPersistencePort.getAttempts(email)).thenReturn(3);

        // Act & Assert
        assertThrows(AccountLockedException.class, () -> authUseCase.login(email, password));
        verify(loginAttemptPersistencePort).incrementAttempts(email);
    }
}
