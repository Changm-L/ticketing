package kr.hhplus.be.server.user.domain.payment.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.dto.PaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.exception.InsufficientBalanceException;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.payment.core.port.in.PaymentUseCase;
import kr.hhplus.be.server.user.domain.payment.core.port.out.PaymentPort;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.core.port.out.ReservationPort;
import kr.hhplus.be.server.user.domain.user.entity.User;
import kr.hhplus.be.server.user.domain.user.repository.UserRepository;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletJpaEntity;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final PaymentPort     paymentPort;
    private final UserRepository  userRepository;
    private final ReservationPort reservationPort;

    @Override
    @Transactional(readOnly = true)
    public List<FindAllPaymentResponse> findAll(
            long userId
    ) {
        return paymentPort.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public PaymentResponse payment(
            long userId,
            long reservationId
    ) {
        Reservation reservation = reservationPort.getById(reservationId);

        User user = userRepository.getById(userId);
        WalletJpaEntity walletJpaEntity = user.getWalletJpaEntity();
        if (reservation.getPrice().compareTo(walletJpaEntity.getBalance()) > 0) {
            throw new InsufficientBalanceException();
        }

        Payment payment = paymentPort.pay(reservation, walletJpaEntity);

        return PaymentResponse.of(reservationId, payment.getPrice());
    }
}
