package com.orange.rouber.converter;

import com.orange.rouber.client.DriverDto;
import com.orange.rouber.client.TripDto;
import com.orange.rouber.client.UserDto;
import com.orange.rouber.client.VehicleDto;
import com.orange.rouber.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Float.valueOf;

public class Converters {

    public static Driver toDriver(DriverDto driverDto) {
        return Driver.builder()
                .name(driverDto.getName())
                .email(driverDto.getEmail())
                .phoneNumber(driverDto.getPhoneNumber())
                .rating(valueOf(driverDto.getRating().orElse(0)))
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .address(userDto.getAddress())
                .build();
    }

    public static Trip toTrip(TripDto tripDto) {
        return Trip.builder()
                .price(tripDto.getPrice())
                .rating(tripDto.getRating().floatValue())
                .startLocation(coordinates(tripDto.getStart_lat(), tripDto.getStart_long()))
                .endLocation(coordinates(tripDto.getEnd_lat(), tripDto.getEnd_long()))
                .build();
    }

    private static Point coordinates(BigDecimal latitude, BigDecimal longitude) {
        return Point.builder()
                .x(latitude)
                .y(longitude)
                .build();
    }


    public static Vehicle toVehicle(VehicleDto vehicleDto) {
        return Vehicle.builder()
                .name(vehicleDto.getName())
                .brand(vehicleDto.getBrand())
                .vin(vehicleDto.getVin())
                .licensePlate(vehicleDto.getLicensePlate())
                .color(vehicleDto.getColor())
                .registerDate(vehicleDto.getRegisterDate())
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static VehicleDto toVehicleDto(Vehicle vehicle) {
        return VehicleDto.builder()
                .name(vehicle.getName())
                .brand(vehicle.getBrand())
                .vin(vehicle.getVin())
                .licensePlate(vehicle.getLicensePlate())
                .color(vehicle.getColor())
                .registerDate(vehicle.getRegisterDate())
                .createdDate(vehicle.getCreatedDate())
                .state(vehicle.getState())
                .build();
    }

    public static List<VehicleDto> toVehicleDtos(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(Converters::toVehicleDto)
                .collect(Collectors.toList());
    }
}
