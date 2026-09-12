package com.airtribe.smart_parking_lot.service;

import com.airtribe.smart_parking_lot.dto.CheckInRequestDTO;
import com.airtribe.smart_parking_lot.dto.FloorResponseDTO;
import com.airtribe.smart_parking_lot.dto.ParkingLotDTO;
import com.airtribe.smart_parking_lot.dto.ParkingSpotResponseDTO;
import com.airtribe.smart_parking_lot.entity.Floor;
import com.airtribe.smart_parking_lot.entity.ParkingLot;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.entity.ParkingTransaction;
import com.airtribe.smart_parking_lot.exceptions.ParkingLotNotFoundException;
import com.airtribe.smart_parking_lot.repository.ParkingLotRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private SharedService sharedService;

    public ParkingLotDTO createParkingLot(ParkingLot parkingLot) {
        for(Floor floor: parkingLot.getFloors()) {
            floor.setParkingLot(parkingLot);

            for(ParkingSpot parkingSpot: floor.getParkingSpots()) {
                parkingSpot.setFloor(floor);
            }
        }
        ParkingLot parkingLotSaved = parkingLotRepository.save(parkingLot);
        return sharedService.mapToParkingLotDTO(parkingLotSaved);
    }

    public ParkingLotDTO getParkingLotById(Long parkingLotId) throws ParkingLotNotFoundException {
        if(parkingLotId < 0) {
            throw new IllegalArgumentException("Invalid id");
        }

        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(parkingLotId);
        if(parkingLotOptional.isEmpty()) {
            throw new ParkingLotNotFoundException("Parking lot not found with id: " + parkingLotId);
        }
        return sharedService.mapToParkingLotDTO(parkingLotOptional.get());
    }

}
