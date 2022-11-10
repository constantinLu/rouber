package com.orange.rouber.client.corepayments;

import java.io.Serializable;

public enum PaymentStatus implements Serializable {
    UNPROCESSED,
    PENDING_AUTHORIZATION,
    PENDING_CONFIRMATION,
    SUCCEEDED,
    FAILED;
}