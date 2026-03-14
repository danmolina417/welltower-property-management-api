-- ============================================================
-- V2: Seed initial data
-- Runs once on first startup after V1; skipped on every restart.
-- ============================================================

-- 1. Manager -------------------------------------------------
INSERT INTO managers (first_name, last_name, email, phone_number, created_at)
VALUES ('John', 'Doe', 'john.doe@example.com', '202-999-1212', NOW());

-- 2. Property ------------------------------------------------
INSERT INTO properties (property_name, address, city, state, zip_code, manager_id, is_active, created_at, updated_at)
SELECT 'Happy Homes', '456 Oak Ave', 'Chicago', 'IL', '60602', id, TRUE, NOW(), NOW()
FROM managers
WHERE email = 'john.doe@example.com';

-- 3. Units ---------------------------------------------------
INSERT INTO units (property_id, unit_number, bedrooms, bathrooms, square_feet, is_active, is_occupied, created_at, updated_at)
SELECT id, '101', '1', '2', 850.0, TRUE, FALSE, NOW(), NOW()
FROM properties WHERE property_name = 'Happy Homes';

INSERT INTO units (property_id, unit_number, bedrooms, bathrooms, square_feet, is_active, is_occupied, created_at, updated_at)
SELECT id, '102', '2', '2', 950.0, TRUE, FALSE, NOW(), NOW()
FROM properties WHERE property_name = 'Happy Homes';

INSERT INTO units (property_id, unit_number, bedrooms, bathrooms, square_feet, is_active, is_occupied, created_at, updated_at)
SELECT id, '103', '1', '1', 600.0, FALSE, FALSE, NOW(), NOW()
FROM properties WHERE property_name = 'Happy Homes';

-- 4. Residents -----------------------------------------------
INSERT INTO residents (property_id, unit_id, first_name, last_name, email, phone_number, monthly_rent, move_in_date, is_active, created_at, updated_at)
SELECT p.id, u.id, 'Robert', 'Smith', 'robert@example.com', '212-550-4444', 1450.00, '2026-01-01', TRUE, NOW(), NOW()
FROM properties p
JOIN units u ON u.property_id = p.id
WHERE p.property_name = 'Happy Homes' AND u.unit_number = '101';

INSERT INTO residents (property_id, unit_id, first_name, last_name, email, phone_number, monthly_rent, move_in_date, is_active, created_at, updated_at)
SELECT p.id, u.id, 'Jane', 'Summers', 'jane@example.com', '939-444-2222', 1600.00, '2026-02-01', TRUE, NOW(), NOW()
FROM properties p
JOIN units u ON u.property_id = p.id
WHERE p.property_name = 'Happy Homes' AND u.unit_number = '102';

-- 5. Mark occupied units -------------------------------------
UPDATE units
SET is_occupied = TRUE
WHERE unit_number IN ('101', '102')
  AND property_id = (SELECT id FROM properties WHERE property_name = 'Happy Homes');
