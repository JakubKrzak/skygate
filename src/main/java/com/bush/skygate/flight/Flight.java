package com.bush.skygate.flight;

import com.bush.skygate.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "flight")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flight extends BaseEntity {

    @Column(name="boarding_at")
    private Instant boardingAt;

    @Column(name="gate_closes_at")
    private Instant gateClosesAt;

    @Column(name="scheduled_departure_date",
            nullable = false)
    private LocalDate scheduledDepartureDate;

    @Column(name="estimated_departure_at",
            nullable = false)
    private Instant estimatedDepartureAt;

    @Column(name="capacity",
            nullable = false)
    private Short capacity;

    @Enumerated(EnumType.STRING)
    @Column(name="status",
            length = 16,
            nullable = false)
    private FlightStatus status = FlightStatus.SCHEDULED;

    @Column(name="flight_number",
            length=8,
            nullable = false)
    private String flightNumber;

    @Column(name="gate",
            length = 4)
    private String gate;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name="origin",
            length = 3,
            nullable = false)
    private String origin;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name="destination",
            length = 3,
            nullable = false)
    private String destination;

    public Flight(String flightNumber, String origin, String destination,
                  Short capacity, LocalDate scheduledDepartureDate ,Instant estimatedDepartureAt) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.capacity = capacity;
        this.scheduledDepartureDate = scheduledDepartureDate;
        this.estimatedDepartureAt = estimatedDepartureAt;
    }

}
