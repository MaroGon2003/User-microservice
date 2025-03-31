package com.powerup.user_microservice.domain.model;

public enum RoleEnum {

    ROLE_ADMIN(1L),
    ROLE_SELLER(2L),
    ROLE_BUYER(3L);

    private final long id;

    RoleEnum(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
