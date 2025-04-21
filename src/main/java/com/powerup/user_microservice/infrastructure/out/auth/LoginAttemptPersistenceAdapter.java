package com.powerup.user_microservice.infrastructure.out.auth;

import com.powerup.user_microservice.domain.spi.ILoginAttemptPersistencePort;
import com.powerup.user_microservice.infrastructure.utils.InfrastructureConstants;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LoginAttemptPersistenceAdapter implements ILoginAttemptPersistencePort {

    private final Map<String, Integer> attemptsCache = new HashMap<>();

    @Override
    public void incrementAttempts(String email) {
        attemptsCache.put(email, attemptsCache.getOrDefault(email, InfrastructureConstants.DEFAULT_ATTEMPTS) + InfrastructureConstants.INCREMENT_STEP);
    }

    @Override
    public void resetAttempts(String email) {
        attemptsCache.remove(email);
    }

    @Override
    public int getAttempts(String email) {
        return attemptsCache.getOrDefault(email, InfrastructureConstants.DEFAULT_ATTEMPTS);
    }

    @Override
    public boolean isBlocked(String email) {
        return getAttempts(email) >= InfrastructureConstants.MAX_ATTEMPTS;
    }

}
