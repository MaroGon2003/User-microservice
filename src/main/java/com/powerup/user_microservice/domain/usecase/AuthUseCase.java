package com.powerup.user_microservice.domain.usecase;

import com.powerup.user_microservice.domain.api.IAuthServicePort;
import com.powerup.user_microservice.domain.exception.AccountLockedException;
import com.powerup.user_microservice.domain.exception.InvalidCredentialsException;
import com.powerup.user_microservice.domain.spi.IUserValidationPort;
import com.powerup.user_microservice.domain.spi.ILoginAttemptPersistencePort;
import com.powerup.user_microservice.domain.utils.DomainConstants;

public class AuthUseCase implements IAuthServicePort {
    private final IUserValidationPort validationPort;
    private final ILoginAttemptPersistencePort loginAttemptPersistencePort;

    public AuthUseCase(IUserValidationPort validationPort, ILoginAttemptPersistencePort loginAttemptPersistencePort) {
        this.validationPort = validationPort;
        this.loginAttemptPersistencePort = loginAttemptPersistencePort;
    }

    @Override
    public String login(String email, String password) {

        if (loginAttemptPersistencePort.isBlocked(email)) {
            throw new AccountLockedException(DomainConstants.ACCOUNT_LOCKED_MESSAGE, email);
        }

        try{

            String token = validationPort.validateUser(email, password);
            loginAttemptPersistencePort.resetAttempts(email);
            return token;

        }catch (Exception e){
            loginAttemptPersistencePort.incrementAttempts(email);
            if (loginAttemptPersistencePort.getAttempts(email) >= DomainConstants.MAX_ATTEMPTS) {
                throw new AccountLockedException(DomainConstants.ACCOUNT_LOCKED_MESSAGE, email);
            }
            throw new InvalidCredentialsException(DomainConstants.INVALID_CREDENTIALS_MESSAGE);
        }

    }


}
