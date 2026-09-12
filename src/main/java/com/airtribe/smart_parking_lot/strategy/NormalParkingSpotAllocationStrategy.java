package com.airtribe.smart_parking_lot.strategy;

import com.airtribe.smart_parking_lot.entity.Floor;
import com.airtribe.smart_parking_lot.entity.ParkingLot;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.entity.Vehicle;
import com.airtribe.smart_parking_lot.enums.Status;
import com.airtribe.smart_parking_lot.exceptions.NoSpotAvailableException;
import org.springframework.stereotype.Component;

@Component
public class NormalParkingSpotAllocationStrategy implements ParkingSpotAllocationStrategy {
    @Override
    public ParkingSpot allocate(ParkingLot parkingLot, Vehicle vehicle) throws NoSpotAvailableException {
        for (Floor floor : parkingLot.getFloors()) {
            for (ParkingSpot parkingSpot : floor.getParkingSpots()) {

                if (Status.AVAILABLE.equals(parkingSpot.getStatus()) &&
                        parkingSpot.getAllowedVehicleType().equals(vehicle.getVehicleType())) {
                    return parkingSpot;
                }

            }
        }
        throw new NoSpotAvailableException("Sorry!! No spot available for the given vehicle type.");
    }
}
