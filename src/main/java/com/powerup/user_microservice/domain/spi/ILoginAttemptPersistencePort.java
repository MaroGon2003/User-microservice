package com.powerup.user_microservice.domain.spi;

public interface ILoginAttemptPersistencePort {

    void incrementAttempts(String email);
    void resetAttempts(String email);
    int getAttempts(String email);
    boolean isBlocked(String email);

}
