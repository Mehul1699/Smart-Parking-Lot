package com.airtribe.smart_parking_lot.service;

import com.airtribe.smart_parking_lot.entity.Vehicle;
import com.airtribe.smart_parking_lot.exceptions.VehicleNotFoundException;
import com.airtribe.smart_parking_lot.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleById(Long vehicleId) throws VehicleNotFoundException {
        if(vehicleId < 0) {
            throw new IllegalArgumentException("Id cannot be less than 0");
        }
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);
        if(vehicleOptional.isEmpty()) {
            throw new VehicleNotFoundException("Vehicle not found with id: " + vehicleId);
        }
        return vehicleOptional.get();
    }

    public Vehicle getVehicleByNumber(String vehicleNumber) throws VehicleNotFoundException {
        if(vehicleNumber==null || vehicleNumber.isEmpty()) {
            throw new IllegalArgumentException("Vehicle Number is a required parameter");
        }
        Optional<Vehicle> vehicleOptional = vehicleRepository.getVehicleByVehicleNumber(vehicleNumber);
        if(vehicleOptional.isEmpty()) {
            throw new VehicleNotFoundException("Vehicle not found with vehicle number: " + vehicleNumber);
        }
        return vehicleOptional.get();
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void deleteVehicle(Long vehicleId) throws VehicleNotFoundException {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);
        if(vehicleOptional.isEmpty()) {
            throw new VehicleNotFoundException("Vehicle not found with id: " + vehicleId);
        }
        vehicleRepository.deleteById(vehicleId);
    }
}
