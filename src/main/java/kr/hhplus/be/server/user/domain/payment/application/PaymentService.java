package kr.hhplus.be.server.user.domain.payment.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.dto.PaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.exception.InsufficientBalanceException;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.payment.core.port.in.PaymentUseCase;
import kr.hhplus.be.server.user.domain.payment.core.port.out.PaymentPort;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.core.port.out.ReservationPort;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;
import kr.hhplus.be.server.user.domain.wallet.core.port.out.WalletPort;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final PaymentPort                 paymentPort;
    private final WalletPort                  walletPort;
    private final ReservationPort             reservationPort;
    private final SeatInventoryReadRepository seatInventoryReadRepository;

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

        Wallet wallet = walletPort.getWalletByUserId(userId);
        if (reservation.getPrice().compareTo(wallet.getBalance()) > 0) {
            throw new InsufficientBalanceException();
        }

        SeatInventoryJpaEntity seatInventoryJpaEntity = seatInventoryReadRepository.getById(reservation.getSeatInventoryId());
        seatInventoryJpaEntity.reserve();
        wallet.use(seatInventoryJpaEntity.getPrice());
        Payment payment = paymentPort.pay(reservation, wallet);

        return PaymentResponse.of(reservationId, payment.getPrice());
    }
}
