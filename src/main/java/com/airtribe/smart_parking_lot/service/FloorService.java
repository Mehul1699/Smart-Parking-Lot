package com.airtribe.smart_parking_lot.service;

import com.airtribe.smart_parking_lot.dto.FloorResponseDTO;
import com.airtribe.smart_parking_lot.dto.ParkingLotDTO;
import com.airtribe.smart_parking_lot.entity.Floor;
import com.airtribe.smart_parking_lot.entity.ParkingLot;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.exceptions.FloorNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.ParkingLotNotFoundException;
import com.airtribe.smart_parking_lot.repository.FloorRepository;
import com.airtribe.smart_parking_lot.repository.ParkingLotRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FloorService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private SharedService sharedService;

    public ParkingLotDTO addFloorToParkingLot(Long lotId, Floor floor) throws ParkingLotNotFoundException, BadRequestException {
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(lotId);
        if (parkingLotOptional.isEmpty()) {
            throw new ParkingLotNotFoundException("Parking lot not found with id: " + lotId + " . Cannot create or assign floor");
        }
        ParkingLot parkingLot = parkingLotOptional.get();
        if (floorRepository.existsByParkingLotIdAndFloorNumber(lotId, floor.getFloorNumber())) {
            throw new BadRequestException("Floor already exists with this floor number in this Parking Lot");
        }
        parkingLot.getFloors().add(floor);
        floor.setParkingLot(parkingLot);

        if (floor.getParkingSpots() != null && !floor.getParkingSpots().isEmpty()) {
            for (ParkingSpot parkingSpot : floor.getParkingSpots()) {
                parkingSpot.setFloor(floor);
            }
        }

        ParkingLot parkingLotSaved = parkingLotRepository.save(parkingLot);

        return sharedService.mapToParkingLotDTO(parkingLotSaved);
    }

    public List<FloorResponseDTO> getFloorsByParkingLotId(Long lotId) throws ParkingLotNotFoundException {

        if (!parkingLotRepository.existsById(lotId)) {
            throw new ParkingLotNotFoundException("Parking lot not found with id: " + lotId + " . Cannot provide floors");
        }

        List<Floor> floors = floorRepository.findByParkingLotId(lotId);

        return sharedService.mapToFloorResponseDTOs(floors);

    }

    public FloorResponseDTO getFloorByParkingLotIdAndFloorId(Long lotId, Long floorId)
            throws ParkingLotNotFoundException, FloorNotFoundException {

        if (!parkingLotRepository.existsById(lotId)) {
            throw new ParkingLotNotFoundException("Parking lot not found with id: " + lotId + " . Cannot provide floors");
        }

        Optional<Floor> floorOptional = floorRepository.findByParkingLotIdAndFloorId(lotId, floorId);

        if (floorOptional.isEmpty()) {
            throw new FloorNotFoundException("Floor not found with Parking lot id: " + lotId + " and floor id: " + floorId);
        }

        Floor floor = floorOptional.get();

        return sharedService.getFloorResponseDTO(floor);

    }
}
