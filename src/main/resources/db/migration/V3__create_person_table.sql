CREATE TABLE person (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    public_id UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_of_birth DATE NOT NULL,
    document_expire_date DATE NOT NULL,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    gender CHAR(1) NOT NULL,
    nationality CHAR(3) NOT NULL,
    document_type CHAR(1) NOT NULL,
    document_issuing_country CHAR(3) NOT NULL,
    document_number_hash CHAR(64) NOT NULL,
    document_number_encrypted VARCHAR(255) NOT NULL,

    CONSTRAINT chk_document_type_status
                    CHECK (document_type IN ('P', 'I')),
    CONSTRAINT chk_gender_status
                    CHECK (gender IN ('F', 'M', 'X')),

    UNIQUE (document_type, document_issuing_country, document_number_hash)

);