package com.airtribe.smart_parking_lot.dto;

import com.airtribe.smart_parking_lot.enums.AllocationStrategy;

import java.util.List;

public class ParkingLotDTO {

    private Long id;
    private String name;
    private String address;
    private AllocationStrategy allocationStrategy;
    private List<FloorResponseDTO> floorResponseDTOS;

    public ParkingLotDTO(Long id, String name, String address, AllocationStrategy allocationStrategy) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.allocationStrategy = allocationStrategy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AllocationStrategy getAllocationStrategy() {
        return allocationStrategy;
    }

    public void setAllocationStrategy(AllocationStrategy allocationStrategy) {
        this.allocationStrategy = allocationStrategy;
    }

    public List<FloorResponseDTO> getFloorResponseDTOS() {
        return floorResponseDTOS;
    }

    public void setFloorResponseDTOS(List<FloorResponseDTO> floorResponseDTOS) {
        this.floorResponseDTOS = floorResponseDTOS;
    }
}
