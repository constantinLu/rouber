package com.orange.rouber.client;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TripDto {

    private BigDecimal price;

    private Long rating;

    private BigDecimal start_lat;
    private BigDecimal start_long;

    private BigDecimal end_lat;
    private BigDecimal end_long;

    private Long requestedByUser;

    private Long assignedTo;
}
