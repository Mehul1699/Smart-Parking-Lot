package com.airtribe.smart_parking_lot.dto;

import java.time.LocalDateTime;

public class CheckOutResponseDTO {

    private Long transactionId;

    private VehicleResponseDTO vehicle;

    private ParkingSpotResponseDTO parkingSpot;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private double fees;

    public CheckOutResponseDTO(Long transactionId, VehicleResponseDTO vehicle, ParkingSpotResponseDTO parkingSpot,
                               LocalDateTime entryTime, LocalDateTime exitTime, double fees) {
        this.transactionId = transactionId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fees = fees;
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

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }
}
