package com.airtribe.smart_parking_lot.controller;

import com.airtribe.smart_parking_lot.dto.FloorResponseDTO;
import com.airtribe.smart_parking_lot.dto.ParkingSpotResponseDTO;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.exceptions.FloorNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.ParkingLotNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.ParkingSpotNotFoundException;
import com.airtribe.smart_parking_lot.service.ParkingSpotService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @PostMapping("/parking-lots/{lotId}/floors/{floorId}/spots")
    public FloorResponseDTO assignSpotToAFloor(@PathVariable Long lotId, @PathVariable Long floorId,
                                               @RequestBody ParkingSpot parkingSpot)
            throws FloorNotFoundException, ParkingLotNotFoundException, BadRequestException {
        return parkingSpotService.assignParkingSpotToAFloor(lotId, floorId, parkingSpot);
    }

    @GetMapping("/parking-lots/{lotId}/floors/{floorId}/spots/{spotId}")
    public ParkingSpotResponseDTO getFloorByLotIdAndFloorIdAndSpotId(@PathVariable Long lotId, @PathVariable Long floorId, @PathVariable Long spotId)
            throws FloorNotFoundException, ParkingLotNotFoundException, ParkingSpotNotFoundException {
        return parkingSpotService.getFloorByLotIdAndFloorIdAndSpotId(lotId, floorId, spotId);
    }

    @GetMapping("/parking-lots/{lotId}/floors/{floorId}/spots")
    public List<ParkingSpotResponseDTO> getAllParkingSpotsByFloorByLotId(@PathVariable Long lotId, @PathVariable Long floorId)
            throws FloorNotFoundException, ParkingLotNotFoundException {
        return parkingSpotService.getAllParkingSpotsByFloorByLotId(lotId, floorId);
    }

}
