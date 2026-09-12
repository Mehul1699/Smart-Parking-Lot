package com.airtribe.smart_parking_lot.dto;

import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.entity.Vehicle;

import java.time.LocalDateTime;

public class CheckInResponseDTO {

    private Long transactionId;

    private VehicleResponseDTO vehicle;

    private ParkingSpotResponseDTO parkingSpot;

    private LocalDateTime entryTime;

    public CheckInResponseDTO(Long transactionId, VehicleResponseDTO vehicle, ParkingSpotResponseDTO parkingSpot, LocalDateTime entryTime) {
        this.transactionId = transactionId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = entryTime;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public VehicleResponseDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleResponseDTO vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpotResponseDTO getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpotResponseDTO parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }
}
