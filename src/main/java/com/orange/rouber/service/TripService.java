package com.orange.rouber.service;

import com.orange.rouber.model.Trip;
import com.orange.rouber.repository.DriverRepository;
import com.orange.rouber.repository.TripRepository;
import com.orange.rouber.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class TripService {

    TripRepository tripRepository;

    UserRepository userRepository;

    DriverRepository driverRepository;

    public TripService(TripRepository tripRepository, UserRepository userRepository, DriverRepository driverRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
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
            tripRepository.save(trip);
        });
    }
}
