package com.orange.rouber.controller;

import com.orange.rouber.client.DriverDto;
import com.orange.rouber.client.DriverProfileDto;
import com.orange.rouber.client.TripInfoDto;
import com.orange.rouber.converter.Converters;
import com.orange.rouber.service.DriverService;
import com.orange.rouber.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.orange.rouber.converter.Converters.toDriverDto;
import static com.orange.rouber.converter.Converters.toVehicleDto;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    private final TripService tripService;

    public DriverController(DriverService driverService, TripService tripService) {
        this.driverService = driverService;
        this.tripService = tripService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDriver(@RequestBody DriverDto driverDto) {
        driverService.registerDriver(Converters.toDriver(driverDto));
    }

    /**
     * Rate driver - this should be deprecated since the rate is based on the trip
     */
    @PutMapping("/{driverId}")
    public void rateDriver(@PathVariable Long driverId, @RequestBody DriverDto driverDto) {
        Assert.notNull(driverDto.getRating(), "Rating must be present");
        driverService.rateDriver(driverId, driverDto.rating());
    }


    /**
     * List trip info with payments attached
     */
    @GetMapping("/{driverId}/trips")
    public List<TripInfoDto> getDriverTripInfo(@PathVariable Long driverId) {
        final var trips = tripService.getDriverTrips(driverId);
        return trips.stream()
                .map(t -> TripInfoDto.builder()
                        .tripDto(Converters.toTripDto(t))
                        .paymentDto(Converters.toPaymentDto(t.getPayment()))
                        .build())
                .collect(Collectors.toList());

    }

    /**
     * Current rating of the driver
     */
    @GetMapping("{driverId}/ratings")
    public DriverDto getDriverRating(@PathVariable Long driverId) {
        final var driver = driverService.getDriver(driverId);
        return DriverDto.builder()
                .rating(driver.getRating())
                .build();
    }

    @GetMapping("{driverId}/profile")
    public DriverProfileDto getDriveProfileInfo(@PathVariable Long driverId) {
        final var driver = driverService.getDriver(driverId);
        final var avgPricePerTrip = tripService.calculateAverageTripPrice(driverId);
        return DriverProfileDto.builder()
                .driverDto(toDriverDto(driver))
                .vehicleDto(toVehicleDto(driver.activeVehicle()))
                .averageTripPrice(avgPricePerTrip)
                .build();
    }
}
