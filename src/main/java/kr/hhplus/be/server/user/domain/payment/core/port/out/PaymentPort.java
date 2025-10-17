package kr.hhplus.be.server.user.domain.payment.core.port.out;

import java.util.List;

import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletJpaEntity;

public interface PaymentPort {
    Payment pay(Reservation reservation, WalletJpaEntity walletJpaEntity);

    List<FindAllPaymentResponse> findAllByUserId(long userId);
}
