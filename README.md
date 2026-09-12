# Smart Parking Lot

A Spring Boot based Smart Parking Lot Management System that manages parking lots, floors, parking spots, vehicles, parking transactions, spot allocation, and parking fee calculation.

The project demonstrates a clean layered architecture along with commonly used design patterns such as **Strategy Pattern** and **Factory Pattern**.

---

## Features

- Create and manage parking lots
- Create and manage floors within parking lots
- Create and manage parking spots
- Register vehicles
- Check-in vehicles into a parking lot
- Automatically assign an available parking spot
- Support multiple parking spot allocation strategies
- Check-out vehicles
- Calculate parking fees using configurable fee strategies
- Maintain parking transactions
- Release parking spots after checkout
- DTO-based API responses to avoid exposing entities directly
- Centralized exception handling
- Thread-safe parking spot assignment

---

## Architecture

The application follows a layered architecture:

```text
Controller
    |
    v
Service
    |
    v
Strategy / Business Logic
    |
    v
Repository
    |
    v
Database
```

### Controller Layer

Responsible for exposing REST APIs and handling HTTP requests.

```text
controller/
├── FloorRestController
├── GlobalExceptionHandler
├── ParkingLotController
├── ParkingSpotController
├── ParkingTransactionController
└── VehicleController
```

Controllers delegate business logic to the service layer rather than directly interacting with repositories.

---

### Service Layer

Contains the application's business logic.

```text
service/
├── FloorService
├── ParkingLotService
├── ParkingSpotService
├── ParkingTransactionService
├── SharedService
├── SpotAssignmentService
└── VehicleService
```

#### ParkingTransactionService

Responsible for parking transaction use cases such as:

- Vehicle check-in
- Vehicle check-out
- Creating parking transactions
- Calculating the parking fee
- Completing transactions

#### SpotAssignmentService

Responsible for parking spot assignment and release.

It uses the configured parking allocation strategy to find an appropriate available spot.

The service is a Spring-managed singleton and the spot assignment operation is synchronized to prevent concurrent threads within the same application instance from selecting the same spot.

---

## Strategy Pattern

The project uses the Strategy Pattern to support different ways of assigning parking spots and calculating parking fees.

### Parking Spot Allocation

```text
ParkingSpotAllocationStrategy
            |
            +--------------------------------+
            |                                |
            v                                v
NormalParkingSpotAllocationStrategy   NearestParkingSpotAllocationStrategy
```

The parking lot contains an allocation strategy:

```text
ParkingLot
    |
    +-- allocationStrategy
```

The `ParkingSpotAllocationStrategyFactory` selects the appropriate strategy.

For example:

```text
NORMAL
   -> NormalParkingSpotAllocationStrategy

NEAREST
   -> NearestParkingSpotAllocationStrategy
```

This allows new allocation strategies to be added without modifying the core transaction logic.

---

## Fee Calculation Strategy

Parking fees are calculated using a separate strategy hierarchy.

```text
FeeCalculationStrategy
          |
          +--------------------------+
          |                          |
          v                          v
NormalFeeCalculationStrategy   DiscountedFeeCalculationStrategy
```

The `FeeCalculationStrategyFactory` is responsible for selecting the appropriate fee calculation strategy.

This keeps fee calculation independent from transaction orchestration.

---

## Factory Pattern

Factories are used to select strategies based on configuration.

```text
ParkingSpotAllocationStrategyFactory
                |
                v
ParkingSpotAllocationStrategy


FeeCalculationStrategyFactory
                |
                v
FeeCalculationStrategy
```

This makes the system easier to extend when new allocation or fee strategies are introduced.

---

## Domain Model

The main entities are:

```text
ParkingLot
    |
    +-- Floor
          |
          +-- ParkingSpot
                    |
                    +-- ParkingTransaction
                              |
                              +-- Vehicle
```

### ParkingLot

Represents a parking facility.

A parking lot contains multiple floors and defines the parking spot allocation strategy.

### Floor

Represents a floor within a parking lot.

### ParkingSpot

Represents an individual parking spot.

A spot belongs to a floor and maintains its availability/status.

### Vehicle

Represents a vehicle entering the parking lot.

### ParkingTransaction

Represents a single parking session.

It contains:

- Transaction ID
- Vehicle
- Parking Spot
- Entry Time
- Exit Time
- Parking Fee

The parking lot for a transaction can be derived through:

```text
ParkingTransaction
        |
        v
ParkingSpot
        |
        v
Floor
        |
        v
ParkingLot
```

A direct `ParkingLot` relationship is therefore not required in `ParkingTransaction`.

---

## DTO Layer

The application uses DTOs for API communication instead of directly exposing JPA entities.

```text
dto/
├── CheckInRequestDTO
├── CheckInResponseDTO
├── CheckOutResponseDTO
├── FloorResponseDTO
├── ParkingLotDTO
├── ParkingSpotResponseDTO
└── VehicleResponseDTO
```

This helps:

- Prevent circular JSON serialization
- Hide internal entity relationships
- Control the API response structure
- Keep persistence models separate from API contracts

---

# REST APIs

## Parking Lot APIs

### Create Parking Lot

```http
POST /parking-lots
```

Example request:

```json
{
  "name": "DLF Parking",
  "address": "Jakheera",
  "allocationStrategy": "NEAREST"
}
```

---

## Floor APIs

### Add Floor

```http
POST /parking-lots/{lotId}/floors
```

### Get Floors

```http
GET /parking-lots/{lotId}/floors
```

---

## Parking Spot APIs

### Add Parking Spot

```http
POST /parking-lots/{lotId}/floors/{floorId}/spots
```

Example request:

```json
{
  "spotNumber": "A-101",
  "vehicleType": "CAR"
}
```

---

## Vehicle APIs

### Register Vehicle

```http
POST /vehicles
```

### Get Vehicle

```http
GET /vehicles/{vehicleId}
```

---

# Parking Transactions

## Check-in

A vehicle is checked into a specific parking lot.

```http
POST /parking-lots/{lotId}/transactions/check-in
```

Request:

```json
{
  "vehicleId": 123
}
```

### Check-in flow

```text
Client
  |
  v
ParkingTransactionController
  |
  v
ParkingTransactionService
  |
  +----> ParkingLotService
  |
  +----> VehicleService
  |
  v
SpotAssignmentService
  |
  v
ParkingSpotAllocationStrategy
  |
  v
Available ParkingSpot
  |
  v
ParkingTransaction
  |
  v
Database
```

The allocation strategy of the parking lot determines which parking spot should be assigned.

---

## Check-out

```http
POST /parking-lots/{lotId}/transactions/{transactionId}/check-out
```

No request body is required.

### Check-out flow

```text
Client
  |
  v
ParkingTransactionController
  |
  v
ParkingTransactionService
  |
  +----> Find active transaction
  |
  +----> Set exit time
  |
  +----> FeeCalculationStrategy
  |
  +----> Calculate fee
  |
  +----> Save transaction
  |
  +----> Release parking spot
  |
  v
Response
```

---

# Concurrency

Parking spot assignment needs to handle concurrent check-in requests.

For example, if two vehicles attempt to enter at the same time:

```text
Vehicle A ----+
              |
              v
       SpotAssignmentService
              ^
              |
Vehicle B ----+
```

The `assignSpot()` operation is synchronized so that, within a single application instance, only one thread can execute the spot assignment operation at a time.

Conceptually:

```java
public synchronized ParkingSpot assignSpot(ParkingLot parkingLot) {
    // Find available spot
    // Reserve spot
    // Return spot
}
```

The important part is that **finding and reserving the spot happen as one operation**.

For a distributed deployment with multiple application instances, database-level locking or atomic database updates should additionally be used to guarantee exclusive spot reservation across instances.

---

# Exception Handling

The application uses custom exceptions for domain-specific failures.

```text
exceptions/
├── FloorNotFoundException
├── NoSpotAvailableException
├── ParkingLotNotFoundException
├── ParkingSpotNotFoundException
├── TransactionNotFoundException
└── VehicleNotFoundException
```

A centralized:

```text
GlobalExceptionHandler
```

handles these exceptions and converts them into appropriate HTTP responses.

Example:

```text
Vehicle not found
        |
        v
VehicleNotFoundException
        |
        v
GlobalExceptionHandler
        |
        v
HTTP 404
```

---

# Project Structure

```text
src/main/java/com/airtribe/smart_parking_lot

├── controller
│   ├── FloorRestController
│   ├── GlobalExceptionHandler
│   ├── ParkingLotController
│   ├── ParkingSpotController
│   ├── ParkingTransactionController
│   └── VehicleController
│
├── dto
│   ├── CheckInRequestDTO
│   ├── CheckInResponseDTO
│   ├── CheckOutResponseDTO
│   ├── FloorResponseDTO
│   ├── ParkingLotDTO
│   ├── ParkingSpotResponseDTO
│   └── VehicleResponseDTO
│
├── entity
│   ├── Floor
│   ├── ParkingLot
│   ├── ParkingSpot
│   ├── ParkingTransaction
│   └── Vehicle
│
├── enums
│   ├── AllocationStrategy
│   ├── FeeStrategy
│   ├── Status
│   └── VehicleType
│
├── exceptions
│   ├── FloorNotFoundException
│   ├── NoSpotAvailableException
│   ├── ParkingLotNotFoundException
│   ├── ParkingSpotNotFoundException
│   ├── TransactionNotFoundException
│   └── VehicleNotFoundException
│
├── repository
│   ├── FloorRepository
│   ├── ParkingLotRepository
│   ├── ParkingSpotRepository
│   ├── ParkingTransactionRepository
│   └── VehicleRepository
│
├── service
│   ├── FloorService
│   ├── ParkingLotService
│   ├── ParkingSpotService
│   ├── ParkingTransactionService
│   ├── SharedService
│   ├── SpotAssignmentService
│   └── VehicleService
│
├── strategy
│   ├── DiscountedFeeCalculationStrategy
│   ├── FeeCalculationStrategy
│   ├── FeeCalculationStrategyFactory
│   ├── NearestParkingSpotAllocationStrategy
│   ├── NormalFeeCalculationStrategy
│   ├── NormalParkingSpotAllocationStrategy
│   ├── ParkingSpotAllocationStrategy
│   └── ParkingSpotAllocationStrategyFactory
│
└── SmartParkingLotApplication
```

---

# Design Principles

The project follows several important design principles.

### Separation of Concerns

Each layer has a specific responsibility:

```text
Controller  -> HTTP/API handling
Service     -> Business logic
Strategy    -> Variable business algorithms
Repository  -> Data access
Entity      -> Domain/persistence model
DTO         -> API contract
```

### Open/Closed Principle

New allocation and fee strategies can be introduced without modifying existing transaction logic.

For example:

```text
+ WeekendFeeCalculationStrategy
+ EVParkingSpotAllocationStrategy
+ VIPParkingSpotAllocationStrategy
```

can be added independently.

### Dependency Injection

Spring's dependency injection is used to manage services, repositories, and strategy components.

### Avoiding Circular References

Entities are not directly exposed through REST responses. DTOs are used to prevent circular serialization caused by bidirectional JPA relationships.

---

# Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST APIs

---

# Running the Application

## Prerequisites

Make sure the following are installed:

- Java
- Maven
- MySQL

## Database Configuration

Configure the database connection in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_parking
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
```

Avoid committing database passwords, API keys, or other secrets to source control.

---

## Run the Application

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start on the configured server port.

---

# Future Improvements

Possible extensions to the system include:

- Database-level locking for distributed deployments
- Payment integration
- Reservation support
- Parking availability API
- EV charging spot support
- Monthly parking passes
- Dynamic pricing
- Admin dashboard
- Authentication and authorization
- Redis-based distributed locking
- Integration tests and Testcontainers
- API documentation using OpenAPI/Swagger

---

# Design Patterns Used

| Pattern | Usage |
|---|---|
| Strategy Pattern | Parking spot allocation |
| Strategy Pattern | Parking fee calculation |
| Factory Pattern | Selecting allocation strategy |
| Factory Pattern | Selecting fee calculation strategy |
| Dependency Injection | Spring-managed components |
| DTO Pattern | API request/response models |

---

## Author

Developed as a Smart Parking Lot backend project using Java and Spring Boot.
