package kr.hhplus.be.server.user.domain.payment.core.port.in;

import java.util.List;

import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.dto.PaymentResponse;

public interface PaymentUseCase {
    PaymentResponse payment(long userId, long reservationId);

    List<FindAllPaymentResponse> findAll(long userId);
}
