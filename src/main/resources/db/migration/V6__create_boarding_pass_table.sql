CREATE TABLE boarding_pass (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    passenger_id BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    cancelled_by UUID,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT  CURRENT_TIMESTAMP,
    scanned_at TIMESTAMPTZ,
    boarded_at TIMESTAMPTZ,
    cancelled_at TIMESTAMPTZ,
    boarded_by UUID,
    status VARCHAR(30) NOT NULL DEFAULT 'ISSUED',
    seat VARCHAR(4),

    CONSTRAINT fk_boarding_pass_passenger
        FOREIGN KEY (passenger_id) REFERENCES passenger (id),

    CONSTRAINT chk_boarding_pass_status
                CHECK ( status IN ('ISSUED', 'SCANNED', 'BOARDED', 'DENIED', 'CANCELLED') )


);
CREATE INDEX idx_boarding_pass_passenger_id ON boarding_pass (passenger_id);
CREATE UNIQUE INDEX uq_boarding_pass_active
    ON boarding_pass (passenger_id)
    WHERE status IN ('ISSUED', 'SCANNED');