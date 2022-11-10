package com.orange.rouber.service;

import com.orange.rouber.model.Driver;
import com.orange.rouber.repository.DriverRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final DriverRepository driverRepository;

    public PaymentService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public void registerDriver(Driver driver) {
        driverRepository.save(driver);
    }
}
