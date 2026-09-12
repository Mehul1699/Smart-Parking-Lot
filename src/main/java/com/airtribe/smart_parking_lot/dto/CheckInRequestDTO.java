package com.airtribe.smart_parking_lot.dto;

import jakarta.validation.constraints.NotNull;

public class CheckInRequestDTO {

    @NotNull
    private Long vehicleId;

    public CheckInRequestDTO(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
