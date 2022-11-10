package com.orange.rouber.service;

import com.orange.rouber.client.corepayments.CorePaymentDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Service
public class CorePaymentService {

    private static final String CORE_PAYMENTS = "http://localhost:8082/payments";

    private final RestTemplate restTemplate;

    public CorePaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public ResponseEntity<CorePaymentDto> authorizePayment(CorePaymentDto unprocessedPayment) {
        HttpEntity<CorePaymentDto> requestEntity = createRequestEntity(unprocessedPayment);
        return restTemplate.exchange(CORE_PAYMENTS, POST, requestEntity, CorePaymentDto.class);
    }

    @Async
    public ResponseEntity<CorePaymentDto> confirmPayment(CorePaymentDto authorizedPayment) {
        HttpEntity<CorePaymentDto> requestEntity = createRequestEntity(authorizedPayment);
        return restTemplate.exchange(CORE_PAYMENTS, PUT, requestEntity, CorePaymentDto.class);
    }

    private HttpEntity<CorePaymentDto> createRequestEntity(CorePaymentDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(request, headers);
    }
}
