package com.airtribe.smart_parking_lot.controller;

import com.airtribe.smart_parking_lot.dto.ParkingLotDTO;
import com.airtribe.smart_parking_lot.entity.ParkingLot;
import com.airtribe.smart_parking_lot.exceptions.ParkingLotNotFoundException;
import com.airtribe.smart_parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping("/parking-lots")
    public ParkingLotDTO createParkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotService.createParkingLot(parkingLot);
    }

    @GetMapping("/parking-lots/{parkingLotId}")
    public ParkingLotDTO getParkingLotById(@PathVariable Long parkingLotId) throws ParkingLotNotFoundException {
        return parkingLotService.getParkingLotById(parkingLotId);
    }

}
