package com.airtribe.smart_parking_lot.dto;

import java.util.List;

public class FloorResponseDTO {
    private Long floorId;
    private Integer floorNumber;
    private List<ParkingSpotResponseDTO> parkingSpotResponseDTOS;

    public FloorResponseDTO(Long floorId, Integer floorNumber) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
    }

    public Long getFloorId() {
        return floorId;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public List<ParkingSpotResponseDTO> getParkingSpotResponseDTOS() {
        return parkingSpotResponseDTOS;
    }

    public void setParkingSpotResponseDTOS(List<ParkingSpotResponseDTO> parkingSpotResponseDTOS) {
        this.parkingSpotResponseDTOS = parkingSpotResponseDTOS;
    }
}
