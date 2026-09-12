package com.airtribe.smart_parking_lot.repository;

import com.airtribe.smart_parking_lot.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

    boolean existsByParkingLotIdAndFloorNumber(Long parkingLotId, Integer floorNumber);

    List<Floor> findByParkingLotId(Long parkingLotId);

    Optional<Floor> findByParkingLotIdAndFloorId(Long parkingLotId, Long floorId);

    boolean existsByParkingLotIdAndFloorId(Long parkingLotId, Long floorId);

}
