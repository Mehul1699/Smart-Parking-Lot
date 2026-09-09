# Smart Parking Lot

A Spring Boot backend application for managing a smart parking lot.

## Objective

Design a low-level architecture for a parking lot system that handles vehicle entry and exit, parking spot allocation, availability tracking, and parking fee calculation.

## Features

* Automatically allocate parking spots based on vehicle type and availability
* Manage vehicle check-in and check-out
* Track entry and exit times
* Calculate parking fees based on parking duration and vehicle type
* Maintain real-time parking spot availability
* Handle multiple vehicles entering and exiting simultaneously

## System Design

The system will include:

* Parking Lot
* Floors
* Parking Spots
* Vehicles
* Parking Transactions
* Fee Calculation

## Technology

* Java
* Spring Boot
* Spring Data JPA
* Maven
* H2 Database

## Project Structure

```text
src/
├── main/
│   ├── java/
│   └── resources/
└── test/
    └── java/
```

## Future Improvements

* Add REST APIs for parking operations
* Add database persistence
* Implement efficient parking spot allocation
* Add concurrency handling
* Add unit and integration tests