package com.orange.rouber.controller;

import com.orange.rouber.client.DriverDto;
import com.orange.rouber.converter.Converters;
import com.orange.rouber.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
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

    /**
     * Rate driver - this should be deprecated since the rate is based on the trip
     */
    @PutMapping("/{driverId}")
    public void rateDriver(@PathVariable Long driverId, @RequestBody DriverDto driverDto) {
        Assert.notNull(driverDto.getRating(), "Rating must be present");
        driverService.rateDriver(driverId, driverDto.rating());
    }


    /**
     * Current rating of the driver
     */
    @GetMapping("{driverId}/ratings")
    public DriverDto driverRating(@PathVariable Long driverId) {
        final var driver = driverService.ratingByDriver(driverId);
        return DriverDto.builder()
                .rating(driver.getRating())
                .build();
    }
}
