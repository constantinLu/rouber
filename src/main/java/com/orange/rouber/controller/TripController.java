package com.orange.rouber.controller;

import com.orange.rouber.client.DriverDto;
import com.orange.rouber.client.TripDto;
import com.orange.rouber.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.orange.rouber.converter.Converters.toTrip;
import static com.orange.rouber.converter.Converters.toTripDtos;
import static com.orange.rouber.util.Validator.checkNotNull;

@RestController
@RequestMapping("/trips")
public class TripController {

    TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createTrip(@RequestBody TripDto tripDto) {
        assert tripDto.getRequestedByUser() != null;
        tripService.createTrip(toTrip(tripDto), tripDto.getRequestedByUser());
    }


    @PutMapping("/{id}")
    public void startTrip(@PathVariable Long id, @RequestBody TripDto tripDto) {
        tripService.startTrip(id, tripDto.getAssignedTo());
    }


    @GetMapping()
    public List<TripDto> driverTrips(@RequestBody TripDto tripDto) {
        return toTripDtos(tripService.driverTrips(tripDto.getAssignedTo()));
    }

    @GetMapping("{tripId}/ratings")
    public DriverDto driverRatings(@PathVariable Long tripId, @RequestBody TripDto tripDto) {
        checkNotNull(tripDto.getAssignedTo());
        final var driver = tripService.ratingByTrip(tripId, tripDto.getAssignedTo());
        return DriverDto.builder()
                .rating(driver.getRating())
                .build();

    }


    //END TRIP
    //ALT ENDPOINT DE UPDATE.
    //@PutMapping
    void endTrip() {
        //DRIVER -> TRIMITE NOTIFICARE LA PAYMENT SERVICE
        //II TRIMITE TOTAL AMOUNT LA FINAL DRIVER -> PAYMENT
        //
        //
    }


    //rouber le pune in coada ->
    // si paymentService -> produceru
    // si rouber -> consumeru` notificare.

}


