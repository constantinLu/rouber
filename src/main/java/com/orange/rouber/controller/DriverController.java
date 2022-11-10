package com.orange.rouber.controller;

import com.orange.rouber.client.DriverDto;
import com.orange.rouber.converter.Converters;
import com.orange.rouber.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDriver(@RequestBody DriverDto driverDto) {
        driverService.registerDriver(Converters.toDriver(driverDto));
    }

    @GetMapping("{driverId}/ratings")
    public DriverDto driverRatings(@PathVariable Long driverId) {
        final var driver = driverService.ratingByDriver(driverId);
        return DriverDto.builder()
                .rating(driver.getRating())
                .build();

    }
}
