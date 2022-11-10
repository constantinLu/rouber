package com.orange.rouber.controller;

import com.orange.rouber.client.TripDto;
import com.orange.rouber.client.TripStatistics;
import com.orange.rouber.client.TripTrigger;
import com.orange.rouber.service.TripService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.time.LocalDate;
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


    /**
     * All available trips
     */
    @GetMapping()
    public List<TripDto> readAvailableTrips() {
        return toTripDtos(tripService.readAvailableTrips());
    }


    /**
     * Any driver can see he`s list of trips
     */
    @GetMapping("/drivers/{driverId}")
    public List<TripDto> getDriverTrips(@PathVariable Long driverId) {
        return toTripDtos(tripService.getDriverTrips(driverId));
    }

    /**
     * Driver rating by trip
     */
    @GetMapping("{tripId}/ratings")
    public TripDto getDriverRatings(@PathVariable Long tripId, @RequestBody TripDto tripDto) {
        Assert.notNull(tripDto.getAssignedTo(), "Driver ID: cannot be null");
        final var trip = tripService.getDriverRatingsByTrip(tripId, tripDto.getAssignedTo());
        return TripDto.builder()
                .rating(trip.getRating())
                .build();
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTrip(@RequestBody TripDto tripDto) {
        assert tripDto.getRequestedByUser() != null;
        tripService.createTrip(toTrip(tripDto), tripDto.getRequestedByUser());
    }


    /**
     * Used for triggering the start of the trip and the finish.
     */
    @PutMapping("/{tripId}")
    public void triggerTrip(@PathVariable Long tripId, @RequestBody TripDto tripDto, @RequestParam TripTrigger tripTrigger) {
        switch (tripTrigger) {
            case START:
                tripService.startTrip(tripId, tripDto.getAssignedTo());
                break;
            case STOP:
                tripService.endTrip(tripId, tripDto.getAssignedTo());
                break;
            default:
                throw new ValidationException("Parameter not supported!");
        }
    }


    /**
     * Rate the trip by the client (user)
     */
    @PutMapping("/{tripId}/ratings")
    public void rateTrip(@PathVariable Long tripId, @RequestBody TripDto tripDto) {
        Assert.notNull(tripDto.getRating(), "Rating must be present");
        Assert.notNull(tripDto.getAssignedTo(), "Driver ID must be present");

        tripService.rateTrip(tripId, tripDto.getAssignedTo(), tripDto.getRating());
    }


    /*
     * added all statistics into one endpoint
     */
    @GetMapping("/drivers/{driverId}/statistics")
    public TripStatistics getStatistics(@PathVariable Long driverId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        final var now = LocalDate.now();
        Assert.isTrue(date.isBefore(LocalDate.now()) || date.isEqual(now), "Date must not be in the future");
        return tripService.calculateStatistics(driverId, date);
    }
}


