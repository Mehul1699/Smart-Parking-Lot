package com.airtribe.smart_parking_lot.dto;

import com.airtribe.smart_parking_lot.enums.Status;
import com.airtribe.smart_parking_lot.enums.VehicleType;

public class ParkingSpotResponseDTO {

    private Long spotId;
    private Integer spotNumber;
    private VehicleType allowedVehicleType;
    private Status status;

    public ParkingSpotResponseDTO(Long spotId, Integer spotNumber, VehicleType allowedVehicleType, Status status) {
        this.spotId = spotId;
        this.spotNumber = spotNumber;
        this.allowedVehicleType = allowedVehicleType;
        this.status = status;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public Integer getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(Integer spotNumber) {
        this.spotNumber = spotNumber;
    }

    public VehicleType getAllowedVehicleType() {
        return allowedVehicleType;
    }

    public void setAllowedVehicleType(VehicleType allowedVehicleType) {
        this.allowedVehicleType = allowedVehicleType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
