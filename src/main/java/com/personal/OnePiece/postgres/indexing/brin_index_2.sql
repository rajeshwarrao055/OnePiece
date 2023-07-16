-- Create a sample table without an index
CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    log_date DATE,
    message TEXT
);

truncate logs;

INSERT INTO logs (log_date, message)
SELECT
    '2021-01-01'::DATE + generate_series - 1 AS log_date,
    'Log message ' || generate_series(1, cast(floor(random() * (1000 - 500 + 1) + 10000) as integer))
FROM generate_series(1, (365 * 20));



select * from logs order by log_date asc;

drop index idx_logs_log_date;
-- Execute a sample query without an index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM logs WHERE log_date > '2025-09-01';
/*
 * Seq Scan on logs  (cost=0.00..1629000.00 rows=66770697 width=23) (actual time=1324.988..6463.003 rows=57341775 loops=1)
  Filter: (log_date > '2025-09-01'::date)
  Rows Removed by Filter: 17476995
Planning Time: 0.106 ms
JIT:
  Functions: 2
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 1.407 ms, Inlining 7.148 ms, Optimization 15.127 ms, Emission 7.631 ms, Total 31.313 ms
Execution Time: 7785.857 ms
 */

-- Create a BRIN index on the "log_date" column
CREATE INDEX idx_logs_log_date ON logs USING BRIN (log_date);

-- Execute the same query with the index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM logs WHERE log_date > '2025-09-01';
/*
 * Seq Scan on logs  (cost=0.00..1478234.60 rows=57501166 width=23) (actual time=751.407..4168.583 rows=57341775 loops=1)
  Filter: (log_date > '2025-09-01'::date)
  Rows Removed by Filter: 17476995
Planning Time: 3.822 ms
JIT:
  Functions: 2
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 0.901 ms, Inlining 6.950 ms, Optimization 17.857 ms, Emission 9.525 ms, Total 35.232 ms
Execution Time: 5460.654 ms
 */

-- still not using BRiN index

/*
One possible explanation : If the query condition log_date > '2022-01-01' matches a significant portion of the rows
in the table, PostgreSQL may determine that a sequential scan is more efficient than utilizing the BRIN index.
The query planner may estimate that accessing the data sequentially without index lookups is faster than scanning the
BRIN index and then accessing the corresponding blocks.
*/

EXPLAIN ANALYZE SELECT * FROM logs WHERE log_date = '2025-09-01';
/*
Bitmap Heap Scan on logs  (cost=57.59..60380.38 rows=10252 width=23) (actual time=5.787..10.254 rows=10493 loops=1)
  Recheck Cond: (log_date = '2025-09-01'::date)
  Rows Removed by Index Recheck: 7181
  Heap Blocks: lossy=128
  ->  Bitmap Index Scan on idx_logs_log_date  (cost=0.00..55.03 rows=17633 width=0) (actual time=3.038..3.039 rows=1280 loops=1)
        Index Cond: (log_date = '2025-09-01'::date)
Planning Time: 0.315 ms
Execution Time: 10.972 ms
*/

-- after dropping index

/*
Gather  (cost=1000.00..934706.28 rows=10252 width=23) (actual time=1364.401..1376.590 rows=10493 loops=1)
  Workers Planned: 2
  Workers Launched: 2
  ->  Parallel Seq Scan on logs  (cost=0.00..932681.08 rows=4272 width=23) (actual time=961.260..961.849 rows=3498 loops=3)
        Filter: (log_date = '2025-09-01'::date)
        Rows Removed by Filter: 24936092
Planning Time: 0.542 ms
JIT:
  Functions: 6
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 3.505 ms, Inlining 99.501 ms, Optimization 33.387 ms, Emission 19.696 ms, Total 156.090 ms
Execution Time: 1378.639 ms
*/
