package com.orange.rouber.client.corepayments;

public enum PaymentStatus {
        UNPROCESSED,
        PENDING_AUTHORIZATION,
        PENDING_CONFIRMATION,
        SUCCEEDED,
        FAILED;
    }