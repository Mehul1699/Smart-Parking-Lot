package com.airtribe.smart_parking_lot.repository;

import com.airtribe.smart_parking_lot.entity.ParkingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingTransactionRepository extends JpaRepository<ParkingTransaction, Long> {
}
