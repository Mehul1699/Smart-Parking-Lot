package com.airtribe.smart_parking_lot.strategy;

import com.airtribe.smart_parking_lot.entity.ParkingTransaction;
import com.airtribe.smart_parking_lot.enums.VehicleType;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DiscountedFeeCalculationStrategy implements FeeCalculationStrategy {

    private static final double discount = 0.80;

    @Override
    public double calculateFee(ParkingTransaction transaction) {
        Duration duration = Duration.between(
                transaction.getEntryTime(),
                transaction.getExitTime()
        );

        long hours = Math.max(1, (long) Math.ceil(duration.toMinutes() / 60.0));

        VehicleType type = transaction.getVehicle().getVehicleType();

        double pricePerHour = ratePerHour(type);

        double normalFee = hours * pricePerHour;

        return normalFee * discount;
    }
}
