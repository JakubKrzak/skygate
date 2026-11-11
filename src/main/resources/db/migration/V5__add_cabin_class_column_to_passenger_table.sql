-- Cabin class is bought with the ticket, so it is known before check-in
ALTER TABLE passenger
    ADD COLUMN cabin_class CHAR(1) DEFAULT 'Y' NOT NULL,
    ADD CONSTRAINT chk_correct_cabin_class_status
        CHECK ( cabin_class IN ('Y', 'W', 'C', 'F') );
