package com.airtribe.smart_parking_lot.controller;

import com.airtribe.smart_parking_lot.dto.FloorResponseDTO;
import com.airtribe.smart_parking_lot.dto.ParkingLotDTO;
import com.airtribe.smart_parking_lot.entity.Floor;
import com.airtribe.smart_parking_lot.exceptions.FloorNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.ParkingLotNotFoundException;
import com.airtribe.smart_parking_lot.service.FloorService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FloorRestController {

    @Autowired
    private FloorService floorService;

    @PostMapping("/parking-lots/{lotId}/floors")
    public ParkingLotDTO addFloorsToParkingLot(@PathVariable Long lotId, @Valid @RequestBody Floor floor)
            throws ParkingLotNotFoundException, BadRequestException {
        return floorService.addFloorToParkingLot(lotId, floor);
    }

    @GetMapping("/parking-lots/{lotId}/floors")
    public List<FloorResponseDTO> getFloorsByParkingLotId(@PathVariable Long lotId) throws ParkingLotNotFoundException {
        return floorService.getFloorsByParkingLotId(lotId);
    }

    @GetMapping("/parking-lots/{lotId}/floors/{floorId}")
    public FloorResponseDTO getFloorByParkingLotIdAndFloorId(@PathVariable Long lotId, @PathVariable Long floorId)
            throws ParkingLotNotFoundException, FloorNotFoundException {
        return floorService.getFloorByParkingLotIdAndFloorId(lotId, floorId);
    }

}
