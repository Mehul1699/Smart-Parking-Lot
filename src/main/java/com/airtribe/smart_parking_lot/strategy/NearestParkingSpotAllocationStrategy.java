package com.airtribe.smart_parking_lot.strategy;

import com.airtribe.smart_parking_lot.entity.Floor;
import com.airtribe.smart_parking_lot.entity.ParkingLot;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.entity.Vehicle;
import com.airtribe.smart_parking_lot.enums.Status;
import com.airtribe.smart_parking_lot.exceptions.NoSpotAvailableException;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class NearestParkingSpotAllocationStrategy implements ParkingSpotAllocationStrategy {
    @Override
    public ParkingSpot allocate(ParkingLot parkingLot, Vehicle vehicle) throws NoSpotAvailableException {

        return parkingLot.getFloors().stream()
                .sorted(Comparator.comparing(Floor::getFloorNumber))
                .flatMap(floor -> floor.getParkingSpots().stream())
                .filter(parkingSpot -> Status.AVAILABLE.equals(parkingSpot.getStatus()))
                .filter(spot ->
                        spot.getAllowedVehicleType().equals(vehicle.getVehicleType()))
                .min(Comparator.comparing(ParkingSpot::getSpotNumber))
                .orElseThrow(() -> new NoSpotAvailableException("Sorry!! No spots available for this vehicle type"));

    }
}
