-- Create a sample table without an index
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    age INT
);

-- Generate sample data with 10,000 rows
INSERT INTO users (name, age)
SELECT
    'User ' || generate_series(1, 1000),
    floor(random() * 100)
FROM generate_series(1, 1000);

select count(1) from users;


-- Execute a sample query without an index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM users WHERE age > 50;

/*
Seq Scan on users  (cost=0.00..8786.40 rows=66944 width=226) (actual time=0.021..88.690 rows=489267 loops=1)
  Filter: (age > 50)
  Rows Removed by Filter: 510733
Planning Time: 0.089 ms
Execution Time: 106.477 ms
*/

-- Create a B-tree index on the "age" column
CREATE INDEX idx_users_age ON users (age);

-- Execute the same query with the index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM users WHERE age > 50;

/*
Bitmap Heap Scan on users  (cost=5499.75..17929.50 rows=492300 width=16) (actual time=35.428..78.828 rows=489267 loops=1)
  Recheck Cond: (age > 50)
  Heap Blocks: exact=6276
  ->  Bitmap Index Scan on idx_users_age  (cost=0.00..5376.68 rows=492300 width=0) (actual time=34.102..34.102 rows=489267 loops=1)
        Index Cond: (age > 50)
Planning Time: 0.403 ms
Execution Time: 95.790 ms
*/

-- Increasing inserted data into 10^9 rows and running above two explain queries with and without index for confirming

--Without Index
/*
Seq Scan on users  (cost=0.00..890401.40 rows=6784011 width=226) (actual time=26.979..9357.335 rows=48999069 loops=1)
  Filter: (age > 50)
  Rows Removed by Filter: 51000931
Planning Time: 0.795 ms
JIT:
  Functions: 2
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 0.244 ms, Inlining 4.025 ms, Optimization 17.523 ms, Emission 5.396 ms, Total 27.187 ms
Execution Time: 10542.810 ms
*/

--With Index

/*
Seq Scan on users  (cost=0.00..1886001.00 rows=33333333 width=226) (actual time=40.488..7808.966 rows=48999069 loops=1)
  Filter: (age > 50)
  Rows Removed by Filter: 51000931
Planning Time: 5.394 ms
JIT:
  Functions: 2
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 1.569 ms, Inlining 5.850 ms, Optimization 21.304 ms, Emission 13.222 ms, Total 41.944 ms
Execution Time: 8973.885 ms
*/

-- Scenario 3 : More pruned age filters
EXPLAIN ANALYZE SELECT * FROM users WHERE age > 50 and age < 53;
/*
Gather  (cost=1000.00..1461605.10 rows=1996515 width=17) (actual time=54.892..2541.249 rows=1998092 loops=1)
  Workers Planned: 2
  Workers Launched: 2
  ->  Parallel Seq Scan on users  (cost=0.00..1260953.60 rows=831881 width=17) (actual time=62.715..2442.203 rows=666031 loops=3)
        Filter: ((age > 50) AND (age < 53))
        Rows Removed by Filter: 32667303
Planning Time: 1.314 ms
JIT:
  Functions: 6
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 3.540 ms, Inlining 113.860 ms, Optimization 44.709 ms, Emission 29.390 ms, Total 191.499 ms
Execution Time: 2606.530 ms
*/

-- Running the same with index :: deteriorated performance :: Table too small ?
/*
Gather  (cost=28226.40..1457335.85 rows=1996667 width=17) (actual time=161.412..3040.419 rows=1998092 loops=1)
  Workers Planned: 2
  Workers Launched: 2
  ->  Parallel Bitmap Heap Scan on users  (cost=27226.40..1256669.15 rows=831945 width=17) (actual time=134.492..2937.512 rows=666031 loops=3)
        Recheck Cond: ((age > 50) AND (age < 53))
        Rows Removed by Index Recheck: 28716471
        Heap Blocks: exact=15829 lossy=183666
        ->  Bitmap Index Scan on idx_users_age  (cost=0.00..26727.24 rows=1996667 width=0) (actual time=134.090..134.090 rows=1998092 loops=1)
              Index Cond: ((age > 50) AND (age < 53))
Planning Time: 0.455 ms
JIT:
  Functions: 6
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 1.981 ms, Inlining 83.995 ms, Optimization 28.874 ms, Emission 19.242 ms, Total 134.091 ms
Execution Time: 3098.420 ms
*/