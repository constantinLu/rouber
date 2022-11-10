package com.orange.rouber.client.corepayments;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
@Value
public class CorePaymentDto implements Serializable {

    BigDecimal amount;

    Optional<String> reason;

    BigDecimal reward;

    UUID requestId;

    String paymentStatus;

    LocalDateTime updatedDate;

    LocalDateTime createdDate;
}
