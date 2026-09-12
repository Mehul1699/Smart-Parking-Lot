package com.airtribe.smart_parking_lot.service;

import com.airtribe.smart_parking_lot.dto.FloorResponseDTO;
import com.airtribe.smart_parking_lot.dto.ParkingSpotResponseDTO;
import com.airtribe.smart_parking_lot.entity.Floor;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import com.airtribe.smart_parking_lot.exceptions.FloorNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.ParkingLotNotFoundException;
import com.airtribe.smart_parking_lot.exceptions.ParkingSpotNotFoundException;
import com.airtribe.smart_parking_lot.repository.FloorRepository;
import com.airtribe.smart_parking_lot.repository.ParkingLotRepository;
import com.airtribe.smart_parking_lot.repository.ParkingSpotRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private SharedService sharedService;

    public FloorResponseDTO assignParkingSpotToAFloor(Long lotId, Long floorId, ParkingSpot parkingSpot)
            throws ParkingLotNotFoundException, FloorNotFoundException, BadRequestException {

        if (!parkingLotRepository.existsById(lotId)) {
            throw new ParkingLotNotFoundException("Parking lot not found with id: " + lotId + " . Cannot create or assign spot to a floor in it.");
        }

        Optional<Floor> floorOptional = floorRepository.findByParkingLotIdAndFloorId(lotId, floorId);

        if (floorOptional.isEmpty()) {
            throw new FloorNotFoundException("Floor not found with Parking lot id: " + lotId + " and floor id: " + floorId);
        }

        Floor floor = floorOptional.get();

        if (parkingSpotRepository.existsByFloorFloorIdAndSpotNumber(floorId, parkingSpot.getSpotNumber())) {
            throw new BadRequestException("A Parking spot already exists with this spot number on floor with id: " + floorId);
        }

        floor.getParkingSpots().add(parkingSpot);
        parkingSpot.setFloor(floor);

        Floor floorSaved = floorRepository.save(floor);

        return sharedService.getFloorResponseDTO(floor);

    }

    public ParkingSpotResponseDTO getFloorByLotIdAndFloorIdAndSpotId(Long lotId, Long floorId, Long spotId)
            throws ParkingLotNotFoundException, FloorNotFoundException, ParkingSpotNotFoundException {

        if (!parkingLotRepository.existsById(lotId)) {
            throw new ParkingLotNotFoundException("Parking lot not found with id: " + lotId + " . Cannot create or assign spot to a floor in it.");
        }

        if (!floorRepository.existsByParkingLotIdAndFloorId(lotId, floorId)) {
            throw new FloorNotFoundException("Floor not found with Parking lot id: " + lotId + " and floor id: " + floorId);
        }

        Optional<ParkingSpot> parkingSpotOptional = parkingSpotRepository.findBySpotIdAndFloorFloorId(spotId, floorId);

        if (parkingSpotOptional.isEmpty()) {
            throw new ParkingSpotNotFoundException("Parking spot not found with spot id: " + spotId + " and floor id: " + floorId);
        }

        return sharedService.mapToParkingSpotResponseDTO(parkingSpotOptional.get());

    }

    public List<ParkingSpotResponseDTO> getAllParkingSpotsByFloorByLotId(Long lotId, Long floorId)
            throws ParkingLotNotFoundException, FloorNotFoundException {

        if (!parkingLotRepository.existsById(lotId)) {
            throw new ParkingLotNotFoundException("Parking lot not found with id: " + lotId + " . Cannot create or assign spot to a floor in it.");
        }

        if (!floorRepository.existsByParkingLotIdAndFloorId(lotId, floorId)) {
            throw new FloorNotFoundException("Floor not found with Parking lot id: " + lotId + " and floor id: " + floorId);
        }

        List<ParkingSpot> parkingSpots = parkingSpotRepository.findByFloorFloorId(floorId);

        return sharedService.getAllParkingSpotDTOs(parkingSpots);

    }
}
