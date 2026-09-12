package com.airtribe.smart_parking_lot.service;

import com.airtribe.smart_parking_lot.dto.FloorResponseDTO;
import com.airtribe.smart_parking_lot.dto.ParkingLotDTO;
import com.airtribe.smart_parking_lot.dto.ParkingSpotResponseDTO;
import com.airtribe.smart_parking_lot.entity.Floor;
import com.airtribe.smart_parking_lot.entity.ParkingLot;
import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SharedService {

    public ParkingLotDTO mapToParkingLotDTO(ParkingLot parkingLot) {
        ParkingLotDTO parkingLotDTO = new ParkingLotDTO(
                parkingLot.getId(), parkingLot.getName(), parkingLot.getAddress(), parkingLot.getAllocationStrategy()
        );

        List<FloorResponseDTO> floorResponseDTOS = new ArrayList<>();

        for (Floor floor : parkingLot.getFloors()) {
            FloorResponseDTO floorResponseDTO = getFloorResponseDTO(floor);

            floorResponseDTOS.add(floorResponseDTO);
        }

        parkingLotDTO.setFloorResponseDTOS(floorResponseDTOS);
        return parkingLotDTO;
    }

    public List<FloorResponseDTO> mapToFloorResponseDTOs(List<Floor> floors) {
        List<FloorResponseDTO> finalResponse = new ArrayList<>();
        for (Floor floor : floors) {
            finalResponse.add(
                    getFloorResponseDTO(floor)
            );
        }
        return finalResponse;
    }

    public FloorResponseDTO getFloorResponseDTO(Floor floor) {
        FloorResponseDTO floorResponseDTO = new FloorResponseDTO(
                floor.getFloorId(), floor.getFloorNumber()
        );

        List<ParkingSpotResponseDTO> parkingSpotResponseDTOS = new ArrayList<>();

        for (ParkingSpot spot : floor.getParkingSpots()) {
            ParkingSpotResponseDTO parkingSpotResponseDTO = new ParkingSpotResponseDTO(
                    spot.getSpotId(), spot.getSpotNumber(), spot.getAllowedVehicleType(), spot.getStatus()
            );
            parkingSpotResponseDTOS.add(parkingSpotResponseDTO);
        }
        floorResponseDTO.setParkingSpotResponseDTOS(parkingSpotResponseDTOS);
        return floorResponseDTO;
    }

    public List<ParkingSpotResponseDTO> getAllParkingSpotDTOs(List<ParkingSpot> parkingSpots) {
        List<ParkingSpotResponseDTO> response = new ArrayList<>();
        for(ParkingSpot parkingSpot: parkingSpots) {
            response.add(
                    mapToParkingSpotResponseDTO(parkingSpot)
            );
        }
        return response;
    }

    public ParkingSpotResponseDTO mapToParkingSpotResponseDTO(ParkingSpot parkingSpot) {
        return new ParkingSpotResponseDTO(
                parkingSpot.getSpotId(), parkingSpot.getSpotNumber(), parkingSpot.getAllowedVehicleType(), parkingSpot.getStatus()
        );
    }


}
