package com.orange.rouber.service;

import com.orange.rouber.client.corepayments.CorePaymentDto;
import com.orange.rouber.model.Payment;
import com.orange.rouber.model.Trip;
import com.orange.rouber.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static com.orange.rouber.client.corepayments.PaymentStatus.PENDING_AUTHORIZATION;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final CorePaymentService corePaymentService;

    public PaymentService(PaymentRepository paymentRepository, CorePaymentService corePaymentService) {
        this.paymentRepository = paymentRepository;
        this.corePaymentService = corePaymentService;
    }

    public Payment createPayment(Trip trip) {
        final var unProcessedPayment = CorePaymentDto.builder()
                .amount(processAuthorizationAmount(trip.getPrice()))
                .requestId(UUID.randomUUID())
                .paymentStatus(PENDING_AUTHORIZATION)
                .reason(Optional.of("Start of trip authorization"))
                .build();

        final var processedPayment = corePaymentService.authorizePayment(unProcessedPayment);

        final var payment = Payment.builder()
                .paidPrice(trip.getPrice())
                .trip(trip)
                .startInitiation(processedPayment.getBody().getCreatedDate())
                .build();
        return paymentRepository.save(payment);
    }


    private BigDecimal processAuthorizationAmount(BigDecimal tripPrice) {
        return tripPrice.min(tripPrice);
    }
}
