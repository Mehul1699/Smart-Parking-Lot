package com.airtribe.smart_parking_lot.controller;

import com.airtribe.smart_parking_lot.entity.Vehicle;
import com.airtribe.smart_parking_lot.exceptions.VehicleNotFoundException;
import com.airtribe.smart_parking_lot.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/vehicles")
    public Vehicle createVehicle(@Valid @RequestBody Vehicle vehicle) {
        return vehicleService.createVehicle(vehicle);
    }

    @GetMapping("/vehicles/{vehicleId}")
    public Vehicle getVehicleById(@PathVariable Long vehicleId) throws VehicleNotFoundException {
        return vehicleService.getVehicleById(vehicleId);
    }

    @GetMapping(value = "/vehicles", params = "vehicleNumber")
    public Vehicle getVehicleByVehicleNumber(@RequestParam String vehicleNumber) throws VehicleNotFoundException {
        return vehicleService.getVehicleByNumber(vehicleNumber);
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @DeleteMapping("/vehicles/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long vehicleId) throws VehicleNotFoundException {
        vehicleService.deleteVehicle(vehicleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
