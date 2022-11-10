package com.orange.rouber.service;

import com.orange.rouber.model.Trip;
import com.orange.rouber.repository.DriverRepository;
import com.orange.rouber.repository.TripRepository;
import com.orange.rouber.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;

    private final UserRepository userRepository;

    private final DriverRepository driverRepository;

    private final PaymentService paymentService;

    public TripService(TripRepository tripRepository, UserRepository userRepository, DriverRepository driverRepository, PaymentService paymentService) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.paymentService = paymentService;
    }

    public void createTrip(Trip trip, Long userId) {
        final var user = userRepository.findById(userId).orElseThrow();
        trip.setRequestedBy(user);
        tripRepository.save(trip);
    }

    public void startTrip(Long tripId, Long driverId) {
        final var assignedDriver = driverRepository.findById(driverId).orElseThrow();
        final var currentTrip = tripRepository.findById(tripId);
        currentTrip.ifPresent(trip -> {
            if (trip.getAssignedTo() == null) {
                trip.setAssignedTo(assignedDriver);
            } else {
                throw new ValidationException("Driver already assigned");
            }

            final var createdPayment = paymentService.createPayment(trip);
            trip.setPayment(createdPayment);
            tripRepository.save(trip);
        });

    }

    public void endTrip(Long tripId, Long driverId) {
        final var currentTrip = tripRepository.findById(tripId).orElseThrow();
        Assert.isTrue(currentTrip.getAssignedTo().getId().equals(driverId), "Driver must be the same");
        paymentService.confirmPayment(currentTrip.getPayment().getId());
    }

    public List<Trip> driverTrips(Long driverId) {
        return tripRepository.findByAssignedTo_Id(driverId);
    }

    public Trip ratingByTrip(Long tripId, Long driverId) {
        return tripRepository.findByIdAndAssignedTo_Id(tripId, driverId);
    }
}
