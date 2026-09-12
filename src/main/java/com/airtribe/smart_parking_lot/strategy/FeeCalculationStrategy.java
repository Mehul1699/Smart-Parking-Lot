package com.airtribe.smart_parking_lot.strategy;

import com.airtribe.smart_parking_lot.entity.ParkingTransaction;
import com.airtribe.smart_parking_lot.enums.VehicleType;

public interface FeeCalculationStrategy {

    public double calculateFee(ParkingTransaction transaction);

    default double ratePerHour(VehicleType vehicleType) {
        return switch (vehicleType) {
            case TWO_WHEELER -> 250.0;
            case CAR -> 500.0;
            case BUS -> 800.0;
        };
    }

}
