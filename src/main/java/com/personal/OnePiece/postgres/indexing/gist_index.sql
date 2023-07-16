-- Create a sample table without an index
CREATE TABLE places (
    id SERIAL PRIMARY KEY,
    location POINT,
    name VARCHAR(100)
);

-- Generate sample data with 10,000 rows
INSERT INTO places (location, name)
SELECT
    point(random() * 180 - 90, random() * 360 - 180),
    'Place ' || generate_series(1, 10000)
FROM generate_series(1, 10000);

/*
Analysis incomplete :: Need to install postgis extension on db
*/

-- Execute a sample query without an index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM places WHERE location @> point(40, -80);

-- Create a GiST index on the "location" column
CREATE INDEX idx_places_location ON places USING GiST (location);

-- Execute the same query with the index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM places WHERE location @> point(40, -80);
