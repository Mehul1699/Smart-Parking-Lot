package com.airtribe.smart_parking_lot.entity;

import com.airtribe.smart_parking_lot.enums.AllocationStrategy;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL)
    private List<Floor> floors;

    @Enumerated(EnumType.STRING)
    private AllocationStrategy allocationStrategy = AllocationStrategy.NORMAL;

    public ParkingLot(Long id, String name, String address, List<Floor> floors, AllocationStrategy allocationStrategy) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.floors = floors;
        this.allocationStrategy = allocationStrategy;
    }

    public ParkingLot(String name, String address, List<Floor> floors, AllocationStrategy allocationStrategy) {
        this.name = name;
        this.address = address;
        this.floors = floors;
        this.allocationStrategy = allocationStrategy;
    }

    public ParkingLot() {}

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

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public AllocationStrategy getAllocationStrategy() {
        return allocationStrategy;
    }

    public void setAllocationStrategy(AllocationStrategy allocationStrategy) {
        this.allocationStrategy = allocationStrategy;
    }
}
