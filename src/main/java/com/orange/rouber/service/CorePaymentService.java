package com.orange.rouber.service;

import com.orange.rouber.client.corepayments.CorePaymentDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Service
public class CorePaymentService {

    private static final String AUTHORIZATION = "http://localhost:8082/payments";
    private static final String CONFIRMATION = "http://localhost:8082/payments";

    private RestTemplate restTemplate;

    public CorePaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public ResponseEntity<CorePaymentDto> authorizePayment(CorePaymentDto payment) {
        final HttpHeaders headers = new HttpHeaders();
        // headers.add(jwtConfiguration.getAuthorizationHeader(), jwtConfiguration.getTokenPrefix() + TokenStorage.getToken());
//        System.setProperty("proxyHost", "localhost.com");
//        System.setProperty("proxyPort", "8080");
        HttpEntity<CorePaymentDto> requestEntity = createRequestEntity(payment);
        return restTemplate.exchange(AUTHORIZATION, POST, requestEntity, CorePaymentDto.class);
    }

    @Async
    public ResponseEntity<String> confirmPayment() {
        final HttpHeaders headers = new HttpHeaders();
        return restTemplate.exchange(CONFIRMATION, GET, new HttpEntity<>(headers), String.class);
    }

    private HttpEntity<CorePaymentDto> createRequestEntity(CorePaymentDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(request, headers);
    }
}
