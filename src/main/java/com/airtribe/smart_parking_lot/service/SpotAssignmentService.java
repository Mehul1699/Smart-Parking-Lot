package com.airtribe.smart_parking_lot.service;

import com.airtribe.smart_parking_lot.entity.ParkingLot;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.entity.Vehicle;
import com.airtribe.smart_parking_lot.enums.Status;
import com.airtribe.smart_parking_lot.exceptions.NoSpotAvailableException;
import com.airtribe.smart_parking_lot.repository.ParkingSpotRepository;
import com.airtribe.smart_parking_lot.strategy.ParkingSpotAllocationStrategy;
import com.airtribe.smart_parking_lot.strategy.ParkingSpotAllocationStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotAssignmentService {

    @Autowired
    private ParkingSpotAllocationStrategyFactory parkingSpotAllocationStrategyFactory;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public synchronized ParkingSpot assignSpot(ParkingLot parkingLot, Vehicle vehicle) throws NoSpotAvailableException {

        ParkingSpotAllocationStrategy spotAllocationStrategy = parkingSpotAllocationStrategyFactory.
                getStrategy(parkingLot.getAllocationStrategy());

        ParkingSpot parkingSpot = spotAllocationStrategy.allocate(parkingLot, vehicle);

        parkingSpot.setStatus(Status.OCCUPIED);

        return parkingSpotRepository.save(parkingSpot);

    }

    public synchronized ParkingSpot checkoutSpot(ParkingSpot parkingSpot) {
        parkingSpot.setStatus(Status.AVAILABLE);
        return parkingSpotRepository.save(parkingSpot);
    }

}
