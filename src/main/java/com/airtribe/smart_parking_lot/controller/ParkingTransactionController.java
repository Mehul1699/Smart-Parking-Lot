package com.airtribe.smart_parking_lot.controller;

import com.airtribe.smart_parking_lot.dto.CheckInRequestDTO;
import com.airtribe.smart_parking_lot.dto.CheckInResponseDTO;
import com.airtribe.smart_parking_lot.dto.CheckOutResponseDTO;
import com.airtribe.smart_parking_lot.entity.ParkingTransaction;
import com.airtribe.smart_parking_lot.exceptions.NoSpotAvailableException;
import com.airtribe.smart_parking_lot.exceptions.ParkingLotNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.TransactionNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.VehicleNotFoundException;
import com.airtribe.smart_parking_lot.service.ParkingTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingTransactionController {

    @Autowired
    private ParkingTransactionService parkingTransactionService;

    @PostMapping("/parking-lots/{lotId}/transactions/check-in")
    public CheckInResponseDTO checkIn(@PathVariable Long lotId, @Valid @RequestBody CheckInRequestDTO checkInRequestDTO)
            throws VehicleNotFoundException, NoSpotAvailableException, ParkingLotNotFoundException {
        return parkingTransactionService.validateAndCheckIn(lotId, checkInRequestDTO);
    }

    @PostMapping("/parking-lots/{lotId}/transactions/{transactionId}/check-out")
    public CheckOutResponseDTO checkOut(@PathVariable Long lotId, @PathVariable Long transactionId)
            throws ParkingLotNotFoundException, TransactionNotFoundException {
        return parkingTransactionService.checkOut(lotId, transactionId);
    }

}
