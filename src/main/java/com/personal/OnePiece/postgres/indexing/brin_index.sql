-- Create a sample table without an index
CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    log_date DATE,
    message TEXT
);

-- Generate sample data with 10,000 rows
INSERT INTO logs (log_date, message)
SELECT
    '2021-01-01'::DATE + CAST(FLOOR(random() * 365) AS INTEGER),
    'Log message ' || generate_series(1, 10000)
FROM generate_series(1, 10000);

-- Execute a sample query without an index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM logs WHERE log_date > '2022-01-01';
/*
Seq Scan on logs  (cost=0.00..1813787.50 rows=29020600 width=40) (actual time=12888.747..12888.762 rows=0 loops=1)
  Filter: (log_date > '2022-01-01'::date)
  Rows Removed by Filter: 100000000
Planning Time: 0.466 ms
JIT:
  Functions: 2
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 1.281 ms, Inlining 7.990 ms, Optimization 32.325 ms, Emission 9.588 ms, Total 51.184 ms
Execution Time: 12890.304 ms
*/

-- Create a BRIN index on the "log_date" column
CREATE INDEX idx_logs_log_date ON logs USING BRIN (log_date);

-- Execute the same query with the index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM logs WHERE log_date > '2022-01-01';
/*
Seq Scan on logs  (cost=0.00..1975515.00 rows=33333333 width=40) (actual time=5709.426..5709.435 rows=0 loops=1)
  Filter: (log_date > '2022-01-01'::date)
  Rows Removed by Filter: 100000000
Planning Time: 2.331 ms
JIT:
  Functions: 2
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 4.337 ms, Inlining 9.088 ms, Optimization 20.368 ms, Emission 11.763 ms, Total 45.556 ms
Execution Time: 5714.094 ms
*/

-- did not use Brin index
