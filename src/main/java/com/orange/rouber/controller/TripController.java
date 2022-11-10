package com.orange.rouber.controller;

import com.orange.rouber.client.DriverDto;
import com.orange.rouber.client.TripDto;
import com.orange.rouber.client.TripTrigger;
import com.orange.rouber.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import static com.orange.rouber.converter.Converters.toTrip;
import static com.orange.rouber.converter.Converters.toTripDtos;

@RestController
@RequestMapping("/trips")
public class TripController {

    TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping()
    public List<TripDto> driverTrips(@RequestBody TripDto tripDto) {
        return toTripDtos(tripService.driverTrips(tripDto.getAssignedTo()));
    }

    @GetMapping("{tripId}/ratings")
    public DriverDto driverRatings(@PathVariable Long tripId, @RequestBody TripDto tripDto) {
        Assert.notNull(tripDto.getAssignedTo(), "Driver ID: cannot be null");
        final var driver = tripService.ratingByTrip(tripId, tripDto.getAssignedTo());
        return DriverDto.builder()
                .rating(driver.getRating())
                .build();

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTrip(@RequestBody TripDto tripDto) {
        assert tripDto.getRequestedByUser() != null;
        tripService.createTrip(toTrip(tripDto), tripDto.getRequestedByUser());
    }

    @PutMapping("/{id}")
    public void triggerTrip(@PathVariable Long id, @RequestBody TripDto tripDto, @RequestParam TripTrigger tripTrigger) {
        switch (tripTrigger) {
            case START:
                tripService.startTrip(id, tripDto.getAssignedTo());
                break;
            case STOP:
                tripService.endTrip(id, tripDto.getAssignedTo());
                break;
            default:
                throw new ValidationException("Parameter not supported!");
        }
    }
}


