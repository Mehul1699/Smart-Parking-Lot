package com.airtribe.smart_parking_lot.service;

import com.airtribe.smart_parking_lot.dto.*;
import com.airtribe.smart_parking_lot.entity.ParkingLot;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.entity.ParkingTransaction;
import com.airtribe.smart_parking_lot.entity.Vehicle;
import com.airtribe.smart_parking_lot.enums.FeeStrategy;
import com.airtribe.smart_parking_lot.enums.VehicleType;
import com.airtribe.smart_parking_lot.exceptions.NoSpotAvailableException;
import com.airtribe.smart_parking_lot.exceptions.ParkingLotNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.TransactionNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.VehicleNotFoundException;
import com.airtribe.smart_parking_lot.repository.ParkingLotRepository;
import com.airtribe.smart_parking_lot.repository.ParkingTransactionRepository;
import com.airtribe.smart_parking_lot.strategy.FeeCalculationStrategy;
import com.airtribe.smart_parking_lot.strategy.FeeCalculationStrategyFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingTransactionService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private final VehicleService vehicleService;

    @Autowired
    private final SpotAssignmentService spotAssignmentService;

    @Autowired
    private ParkingTransactionRepository parkingTransactionRepository;

    @Autowired
    private FeeCalculationStrategyFactory feeCalculationStrategyFactory;

    public ParkingTransactionService(VehicleService vehicleService, SpotAssignmentService spotAssignmentService) {
        this.vehicleService = vehicleService;
        this.spotAssignmentService = spotAssignmentService;
    }

    public CheckInResponseDTO validateAndCheckIn(Long parkingLotId, @Valid CheckInRequestDTO checkInRequestDTO)
            throws ParkingLotNotFoundException, VehicleNotFoundException, NoSpotAvailableException {

        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(parkingLotId);
        if (parkingLotOptional.isEmpty()) {
            throw new ParkingLotNotFoundException("Parking lot not found with id: " + parkingLotId);
        }
        ParkingLot parkingLot = parkingLotOptional.get();

        Long vehicleId = checkInRequestDTO.getVehicleId();
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

        ParkingSpot parkingSpot = spotAssignmentService.assignSpot(parkingLot, vehicle);

        ParkingTransaction parkingTransaction = new ParkingTransaction(vehicle, parkingSpot, LocalDateTime.now());

        ParkingTransaction transactionSaved = parkingTransactionRepository.save(parkingTransaction);

        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO(vehicle.getVehicleId(), vehicle.getVehicleNumber(), vehicle.getVehicleType());

        ParkingSpotResponseDTO parkingSpotResponseDTO = new ParkingSpotResponseDTO(parkingSpot.getSpotId(), parkingSpot.getSpotNumber(),
                parkingSpot.getAllowedVehicleType(), parkingSpot.getStatus());

        return new CheckInResponseDTO(
                transactionSaved.getTransactionId(), vehicleResponseDTO, parkingSpotResponseDTO, transactionSaved.getEntryTime()
        );

    }

    public CheckOutResponseDTO checkOut(Long parkingLotId, Long transactionId)
            throws ParkingLotNotFoundException, TransactionNotFoundException {

        if (!parkingLotRepository.existsById(parkingLotId)) {
            throw new ParkingLotNotFoundException("Parking lot not found with id: " + parkingLotId);
        }

        Optional<ParkingTransaction> parkingTransactionOptional = parkingTransactionRepository.findById(transactionId);
        if (parkingTransactionOptional.isEmpty()) {
            throw new TransactionNotFoundException("Sorry! No Vehicle Parking Transaction found for transaction Id: " + transactionId);
        }
        ParkingTransaction parkingTransaction = parkingTransactionOptional.get();
        parkingTransaction.setExitTime(LocalDateTime.now());

        Duration duration = Duration.between(
                parkingTransaction.getEntryTime(),
                parkingTransaction.getExitTime()
        );

        long hours = Math.max(1, (long) Math.ceil(duration.toMinutes() / 60.0));

        Vehicle vehicle = parkingTransaction.getVehicle();

        ParkingSpot parkingSpotSaved = spotAssignmentService.checkoutSpot(parkingTransaction.getParkingSpot());
        parkingTransaction.setParkingSpot(parkingSpotSaved);

        FeeCalculationStrategy feeCalculationStrategy = feeCalculationStrategyFactory.
                identifyCalculationStrategy(hours > 5 ? FeeStrategy.DISCOUNTED : FeeStrategy.NORMAL);

        double fees = feeCalculationStrategy.calculateFee(parkingTransaction);

        parkingTransaction.setFee(fees);

        ParkingTransaction parkingTransactionSaved = parkingTransactionRepository.save(parkingTransaction);

        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO(vehicle.getVehicleId(), vehicle.getVehicleNumber(), vehicle.getVehicleType());

        ParkingSpotResponseDTO parkingSpotResponseDTO = new ParkingSpotResponseDTO(parkingSpotSaved.getSpotId(), parkingSpotSaved.getSpotNumber(),
                parkingSpotSaved.getAllowedVehicleType(), parkingSpotSaved.getStatus());

        return new CheckOutResponseDTO(
                parkingTransactionSaved.getTransactionId(), vehicleResponseDTO, parkingSpotResponseDTO, parkingTransactionSaved.getEntryTime(),
                parkingTransactionSaved.getExitTime(), parkingTransactionSaved.getFee()
        );

    }
}
