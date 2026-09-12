package com.airtribe.smart_parking_lot.strategy;

import com.airtribe.smart_parking_lot.enums.AllocationStrategy;
import org.springframework.stereotype.Component;

@Component
public class ParkingSpotAllocationStrategyFactory {

    private final NearestParkingSpotAllocationStrategy nearestParkingSpotAllocationStrategy;
    private final NormalParkingSpotAllocationStrategy normalParkingSpotAllocationStrategy;

    public ParkingSpotAllocationStrategyFactory(NearestParkingSpotAllocationStrategy nearestParkingSpotAllocationStrategy,
                                                NormalParkingSpotAllocationStrategy normalParkingSpotAllocationStrategy) {
        this.nearestParkingSpotAllocationStrategy = nearestParkingSpotAllocationStrategy;
        this.normalParkingSpotAllocationStrategy = normalParkingSpotAllocationStrategy;
    }

    public ParkingSpotAllocationStrategy getStrategy(AllocationStrategy allocationStrategy) {
        return switch (allocationStrategy) {
            case NORMAL -> normalParkingSpotAllocationStrategy;
            case NEAREST -> nearestParkingSpotAllocationStrategy;
        };
    }

}
