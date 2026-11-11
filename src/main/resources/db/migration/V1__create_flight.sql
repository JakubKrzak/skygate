CREATE TABLE flight (
    id  BIGINT GENERATED ALWAYS  AS IDENTITY PRIMARY KEY,
    public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT  CURRENT_TIMESTAMP,
    boarding_at TIMESTAMPTZ,
    gate_closes_at TIMESTAMPTZ,
    scheduled_departure_date DATE NOT NULL ,
    estimated_departure_at TIMESTAMPTZ NOT NULL ,
    capacity SMALLINT NOT NULL CHECK (capacity > 0),
    status VARCHAR(16) NOT NULL DEFAULT 'SCHEDULED',
    flight_number VARCHAR(8) NOT NULL ,
    gate VARCHAR(4) ,
    origin CHAR(3) NOT NULL ,
    destination CHAR(3) NOT NULL,


    CONSTRAINT check_flight_status
         CHECK(status IN ('SCHEDULED', 'BOARDING', 'CLOSED', 'DEPARTED', 'CANCELLED')),

    CONSTRAINT unique_flight_number UNIQUE (flight_number, scheduled_departure_date),

    CONSTRAINT chk_flight_route CHECK (origin <> destination),
    CONSTRAINT chk_gate_closes CHECK (gate_closes_at > boarding_at)


);