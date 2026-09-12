package com.airtribe.smart_parking_lot.repository;

import com.airtribe.smart_parking_lot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    public Optional<Vehicle> getVehicleByVehicleNumber(String vehicleNumber);

}
