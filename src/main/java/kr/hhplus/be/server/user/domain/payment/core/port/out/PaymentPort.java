package kr.hhplus.be.server.user.domain.payment.core.port.out;

import java.util.List;

import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;

public interface PaymentPort {
    Payment pay(Reservation reservation, Wallet wallet);

    List<FindAllPaymentResponse> findAllByUserId(long userId);
}
