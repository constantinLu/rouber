package com.orange.rouber.client;

import lombok.*;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RatingDto {

    Float rating;

    Optional<Long> driverId;

    Optional<Long> tripId;
}
