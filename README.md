# SkyGate

SkyGate is a boarding pass management and verification platform, modelled on how
passengers are processed at an airport. It is being designed as a **distributed
system** in which independent services own separate responsibilities and
communicate over the network.

> **Status:** work in progress. Part 1 (DCS) is under development, Part 2 (Gate) is planned.

## Architecture

| Part | Service | Responsibility | Status |
|---|---|---|---|
| 1 | **DCS** (Departure Control System) | Flights, bookings, passengers and boarding passes; the source of truth for who is allowed to fly | In progress |
| 2 | **Gate** | Scans boarding passes at the gate and decides whether a passenger may board | Planned |

## Planned boarding flow

1. A boarding pass barcode (BCBP) is scanned at the gate.
2. The Gate service asks the AODB (Airport Operational Database) whether the flight exists and its gate is open.
3. It asks the DCS whether the boarding pass exists.
4. It compares the scanned data with the DCS record.
5. The passenger is accepted or rejected.

## Roles (planned)

| Role | Access |
|---|---|
| **Admin** | Manages users, flights and operators; full visibility |
| **Flight operator** | Manages their own flights and verifies boarding passes |
| **Passenger** | Views their own bookings and boarding passes |

## Tech stack

- Java 21, Spring Boot 4.1
- Spring Web MVC, Spring Data JPA, Bean Validation, Actuator
- PostgreSQL with Flyway migrations
- Docker Compose, Maven
- JUnit, Mockito, AssertJ

## Implemented so far

- Flights: create, get by public ID, search with pagination
- Bookings: data model and booking reference generation
- Centralized error handling
- Versioned database schema

## Running locally

Requirements: JDK 21 and Docker.

1. Copy `.env.example` to `.env` and fill in the values.
2. Start the application:

```bash
./mvnw spring-boot:run
```

Docker Compose starts the database automatically.

## Roadmap

- [ ] Complete the DCS: bookings, passengers and boarding passes
- [ ] Authentication and role-based authorization (Spring Security)
- [ ] Gate service and the boarding flow
- [ ] Controller tests and Testcontainers integration tests
