package com.orange.rouber.controller;

import com.orange.rouber.client.corepayments.CorePaymentDto;
import com.orange.rouber.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/driver/{driverId}")
    List<CorePaymentDto> readDriverPayments(@PathVariable Long driverId) {
        return paymentService.readDriverPayments(driverId);
    }
}
