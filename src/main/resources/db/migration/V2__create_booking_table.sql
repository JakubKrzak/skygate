CREATE TABLE booking (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT  CURRENT_TIMESTAMP,
    status VARCHAR(25) NOT NULL DEFAULT 'CONFIRMED',
    channel VARCHAR(40) NOT NULL,
    contact_name VARCHAR(60) NOT NULL,
    contact_email VARCHAR(254) NOT NULL,
    contact_phone VARCHAR(20) NOT NULL,
    language CHAR(2),
    booking_reference CHAR(6) NOT NULL UNIQUE,

    CONSTRAINT chk_contact_email CHECK ( contact_email LIKE '%_@__%.__%' ),
    CONSTRAINT chk_status CHECK ( status IN ('CONFIRMED', 'CANCELLED') ),
    CONSTRAINT chk_language CHECK ( language IN ('pl', 'en') )
);