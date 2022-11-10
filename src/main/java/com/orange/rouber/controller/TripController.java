package com.orange.rouber.controller;

import com.orange.rouber.client.TripDto;
import com.orange.rouber.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.orange.rouber.converter.Converters.toTrip;

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
        tripService.createTrip(toTrip(tripDto), tripDto.getRequestedByUser());
    }


    //GET PE TRIPS WHERE DRIVER ID = NULL -> PT A VEDEA DRIVERII PE TOATE
    @PutMapping("/{id}")
    public void startTrip(@PathVariable Long id, @RequestBody TripDto tripDto) {
        tripService.startTrip(id, tripDto.getAssignedTo());
    }


    //@PutMapping
    void updateDriverId() {

    }
    //UPDATE WITH DRIVER ID.
    //aici in service dupa ce e updatat driverul -> apelez endpointul de payment.
    // CREEZ ENTITATEA DE PAYMENTS -> PaymentService.
    // FA LEGATURA CU TRIPS.
    // creeaza  startInitiation de pe payments cand crezi. (confirmation sa fie null)
    //chem clientul de PAYMENT in serviciul meu


    //END TRIP
    //ALT ENDPOINT DE UPDATE.
    //@PutMapping
    void updatePriceTrip() {
        //DRIVER -> TRIMITE NOTIFICARE LA PAYMENT SERVICE
        //II TRIMITE TOTAL AMOUNT LA FINAL DRIVER -> PAYMENT
        //
        //
    }


    //rouber le pune in coada ->
    // si paymentService -> produceru
    // si rouber -> consumeru` notificare.

}


