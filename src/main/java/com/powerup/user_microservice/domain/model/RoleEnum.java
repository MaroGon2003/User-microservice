package com.powerup.user_microservice.domain.model;

public enum RoleEnum {

    ADMIN(1L),
    SELLER(2L),
    BUYER(3L);

    private final long id;

    RoleEnum(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
