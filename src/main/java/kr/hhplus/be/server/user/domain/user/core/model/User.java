package kr.hhplus.be.server.user.domain.user.core.model;

import java.time.LocalDateTime;

import kr.hhplus.be.server.user.domain.user.core.constant.Role;

public class User {
    private long          id;
    private String        email;
    private String        name;
    private String        password;
    private Role          role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(
            long id,
            String email,
            String name,
            String password,
            Role role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private User(
            String email,
            String name,
            String password,
            Role role
    ) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public static User create(
            String email,
            String name,
            String password
    ) {
        return new User(
                email,
                name,
                password,
                Role.USER
        );
    }

    public static User of(
            long id,
            String email,
            String name,
            String password,
            Role role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new User(
                id,
                email,
                name,
                password,
                role,
                createdAt,
                updatedAt
        );
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
