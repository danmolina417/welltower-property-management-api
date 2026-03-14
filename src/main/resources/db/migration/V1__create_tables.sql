-- ============================================================
-- V1: Create all schema tables
-- Runs once on first startup; skipped on every restart after.
-- ============================================================

CREATE TABLE IF NOT EXISTS managers (
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255),
    created_at   TIMESTAMP    NOT NULL,
    updated_at   TIMESTAMP,
    CONSTRAINT uq_managers_email UNIQUE (email)
);

CREATE INDEX IF NOT EXISTS idx_manager_email ON managers (email);

-- ----------------------------------------------------------------

CREATE TABLE IF NOT EXISTS properties (
    id            BIGSERIAL PRIMARY KEY,
    property_name VARCHAR(255) NOT NULL,
    address       VARCHAR(255),
    city          VARCHAR(255),
    state         VARCHAR(255),
    zip_code      VARCHAR(255),
    manager_id    BIGINT REFERENCES managers (id),
    is_active     BOOLEAN   NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP NOT NULL,
    CONSTRAINT uq_properties_property_name UNIQUE (property_name)
);

CREATE INDEX IF NOT EXISTS idx_property_manager ON properties (manager_id);

-- ----------------------------------------------------------------

CREATE TABLE IF NOT EXISTS units (
    id          BIGSERIAL PRIMARY KEY,
    property_id BIGINT       NOT NULL REFERENCES properties (id),
    unit_number VARCHAR(255) NOT NULL,
    bedrooms    VARCHAR(255),
    bathrooms   VARCHAR(255),
    square_feet DOUBLE PRECISION,
    is_active   BOOLEAN   NOT NULL DEFAULT TRUE,
    is_occupied BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_unit_property ON units (property_id);
CREATE INDEX IF NOT EXISTS idx_unit_number   ON units (property_id, unit_number);

-- ----------------------------------------------------------------

CREATE TABLE IF NOT EXISTS residents (
    id            BIGSERIAL PRIMARY KEY,
    property_id   BIGINT          NOT NULL REFERENCES properties (id),
    unit_id       BIGINT REFERENCES units (id),
    first_name    VARCHAR(255)    NOT NULL,
    last_name     VARCHAR(255)    NOT NULL,
    email         VARCHAR(255),
    phone_number  VARCHAR(255),
    monthly_rent  DECIMAL(38, 2)  NOT NULL,
    move_in_date  DATE            NOT NULL,
    move_out_date DATE,
    is_active     BOOLEAN   NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_resident_property   ON residents (property_id);
CREATE INDEX IF NOT EXISTS idx_resident_unit        ON residents (unit_id);
CREATE INDEX IF NOT EXISTS idx_resident_move_dates  ON residents (move_in_date, move_out_date);
