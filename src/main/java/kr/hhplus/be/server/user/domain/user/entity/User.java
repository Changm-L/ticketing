package kr.hhplus.be.server.user.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server._core.infrastructure.convertor.PasswordConverter;
import kr.hhplus.be.server.user.domain.auth.dto.request.SignUpRequest;
import kr.hhplus.be.server.user.domain.user.constant.Role;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletJpaEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Convert(converter = PasswordConverter.class)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private WalletJpaEntity walletJpaEntity;

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

    private void setWalletJpaEntity(WalletJpaEntity walletJpaEntity) {
        this.walletJpaEntity = walletJpaEntity;
    }

    public static User of(SignUpRequest request) {
        User user = new User(
                request.email(),
                request.name(),
                request.password(),
                Role.USER
        );
        WalletJpaEntity walletJpaEntity = WalletJpaEntity.of(user);
        user.setWalletJpaEntity(walletJpaEntity);

        return user;

    }

}
