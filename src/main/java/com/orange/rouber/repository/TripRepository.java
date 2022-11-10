package com.orange.rouber.repository;

import com.orange.rouber.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {


    Optional<Trip> findTripByIdAndAssignedTo_idNotNull(Long id);


}
