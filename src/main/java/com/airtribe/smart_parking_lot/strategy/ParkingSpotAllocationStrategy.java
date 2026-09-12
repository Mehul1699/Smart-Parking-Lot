package com.airtribe.smart_parking_lot.strategy;

import com.airtribe.smart_parking_lot.entity.ParkingLot;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.entity.Vehicle;
import com.airtribe.smart_parking_lot.exceptions.NoSpotAvailableException;

public interface ParkingSpotAllocationStrategy {

    ParkingSpot allocate(ParkingLot parkingLot, Vehicle vehicle) throws NoSpotAvailableException;

}
