package com.orange.rouber.controller;

import com.orange.rouber.client.VehicleDto;
import com.orange.rouber.converter.Converters;
import com.orange.rouber.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.orange.rouber.converter.Converters.toVehicleDtos;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerVehicle(@RequestBody VehicleDto vehicleDto) {
        vehicleService.registerVehicle(Converters.toVehicle(vehicleDto), vehicleDto.getOwnerId());
    }

    @GetMapping("/history")
    public List<VehicleDto> getVehicleHistory() {
        return toVehicleDtos(vehicleService.getVehicleHistory());
    }
}
