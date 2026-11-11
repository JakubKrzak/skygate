CREATE TABLE passenger (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    flight_id BIGINT NOT NULL,
    person_id BIGINT NOT NULL,
    booking_id BIGINT NOT NULL,
    ticket_number CHAR(13) NOT NULL UNIQUE,
    status VARCHAR(30) NOT NULL DEFAULT 'BOOKED',
    passenger_type CHAR(3) NOT NULL DEFAULT 'ADT',
    accompanying_passenger_id BIGINT,

    CONSTRAINT fk_passenger_flight
        FOREIGN KEY (flight_id) REFERENCES flight (id),

    CONSTRAINT uq_passenger_flight_person
        UNIQUE (person_id, flight_id),

    CONSTRAINT fk_passenger_person
        FOREIGN KEY (person_id) REFERENCES person (id),

    CONSTRAINT fk_passenger_booking
        FOREIGN KEY (booking_id) REFERENCES booking (id),

    CONSTRAINT fk_passenger_accompanying
        FOREIGN KEY (accompanying_passenger_id) REFERENCES passenger (id),

    CONSTRAINT chk_passenger_type
                       CHECK ( passenger_type IN ('ADT', 'CHD', 'INF') ),

    CONSTRAINT  chk_passenger_status
                       CHECK ( status IN ('BOOKED', 'CANCELLED') ),

    CONSTRAINT chk_inf_have_adult
                        CHECK ( passenger_type <> 'INF' OR accompanying_passenger_id IS NOT NULL),

    CONSTRAINT uq_passenger_accompanying
                       UNIQUE (accompanying_passenger_id),

    CONSTRAINT chk_length_of_ticket
                       CHECK ( char_length(ticket_number) = 13)

);
CREATE INDEX idx_passenger_booking_id ON passenger (booking_id);
CREATE INDEX idx_passenger_flight_id ON passenger (flight_id);
