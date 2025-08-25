package kr.hhplus.be.server.domain.user.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import kr.hhplus.be.server._core.infrastructure.convertor.PasswordConverter;
import kr.hhplus.be.server.domain.auth.dto.request.SignUpRequest;
import kr.hhplus.be.server.domain.user.constant.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    @Convert(converter = PasswordConverter.class)
    String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;

    public User(SignUpRequest request) {
        this.email = request.email();
        this.name = request.name();
        this.password = request.password();
        this.role = Role.USER;
    }

}
