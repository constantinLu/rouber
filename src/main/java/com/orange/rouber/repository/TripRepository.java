package com.orange.rouber.repository;

import com.orange.rouber.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByAssignedTo_Id(Long id);

    Trip findByIdAndAssignedTo_Id(Long tripId, Long driverId);

}
