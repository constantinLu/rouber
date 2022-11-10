package com.orange.rouber.client;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.orange.rouber.model.Vehicle.State;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class VehicleDto {

    private String name;

    private String brand;

    private String vin;

    private String licensePlate;

    private String color;

    private LocalDate registerDate;

    private LocalDateTime createdDate;

    private State state;

    //driver
    private Long ownerId;

}
