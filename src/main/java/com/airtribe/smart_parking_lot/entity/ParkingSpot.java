package com.airtribe.smart_parking_lot.entity;

import com.airtribe.smart_parking_lot.enums.Status;
import com.airtribe.smart_parking_lot.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long spotId;

    @NotNull
    private Integer spotNumber;

    @ManyToOne
    private Floor floor;

    @Enumerated(EnumType.STRING)
    private VehicleType allowedVehicleType;

    @Enumerated(EnumType.STRING)
    private Status status;

    public ParkingSpot(Long spotId, Integer spotNumber, Floor floor, VehicleType allowedVehicleType, Status status) {
        this.spotId = spotId;
        this.spotNumber = spotNumber;
        this.floor = floor;
        this.allowedVehicleType = allowedVehicleType;
        this.status = status;
    }

    public ParkingSpot(Integer spotNumber, Floor floor, VehicleType allowedVehicleType, Status status) {
        this.spotNumber = spotNumber;
        this.floor = floor;
        this.allowedVehicleType = allowedVehicleType;
        this.status = status;
    }

    public ParkingSpot() {}

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
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

    public Integer getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(Integer spotNumber) {
        this.spotNumber = spotNumber;
    }
}
