package com.airtribe.smart_parking_lot.repository;

import com.airtribe.smart_parking_lot.entity.ParkingLot;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    boolean existsById(@NonNull Long id);

}
