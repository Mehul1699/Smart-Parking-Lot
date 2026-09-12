package com.airtribe.smart_parking_lot.repository;

import com.airtribe.smart_parking_lot.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    boolean existsByFloorFloorIdAndSpotNumber(Long floorId, Integer spotNumber);

    Optional<ParkingSpot> findBySpotIdAndFloorFloorId(Long spotId, Long floorId);

    List<ParkingSpot> findByFloorFloorId(Long floorId);

}
