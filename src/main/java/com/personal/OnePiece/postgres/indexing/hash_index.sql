-- Create a sample table without an index
CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    zipcode VARCHAR(10),
    city VARCHAR(100),
    state VARCHAR(100)
);

-- Generate sample data with 10,000 rows
INSERT INTO addresses (zipcode, city, state)
SELECT
    floor(random() * 100000)::VARCHAR(10),
    'City ' || generate_series(1, 10000),
    'State ' || generate_series(1, 10000)
FROM generate_series(1, 10000);

-- Execute a sample query without an index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM addresses WHERE zipcode = '12345';
/*
Gather  (cost=1000.00..803451.98 rows=58824 width=478) (actual time=155.058..3468.880 rows=973 loops=1)
  Workers Planned: 2
  Workers Launched: 2
  ->  Parallel Seq Scan on addresses  (cost=0.00..796569.58 rows=24510 width=478) (actual time=98.644..3369.874 rows=324 loops=3)
        Filter: ((zipcode)::text = '12345'::text)
        Rows Removed by Filter: 33333009
Planning Time: 2.481 ms
JIT:
  Functions: 6
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 1.479 ms, Inlining 112.171 ms, Optimization 55.796 ms, Emission 37.883 ms, Total 207.330 ms
Execution Time: 3470.053 ms
*/

-- Create a Hash index on the "zipcode" column
CREATE INDEX idx_addresses_zipcode ON addresses USING HASH (zipcode);

-- Execute the same query with the index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM addresses WHERE zipcode = '12345';
/*
Gather  (cost=15515.00..1235954.14 rows=500000 width=478) (actual time=35.087..138.635 rows=973 loops=1)
  Workers Planned: 2
  Workers Launched: 2
  ->  Parallel Bitmap Heap Scan on addresses  (cost=14515.00..1184954.14 rows=208333 width=478) (actual time=42.377..91.616 rows=324 loops=3)
        Recheck Cond: ((zipcode)::text = '12345'::text)
        Heap Blocks: exact=687
        ->  Bitmap Index Scan on idx_addresses_zipcode  (cost=0.00..14390.00 rows=500000 width=0) (actual time=1.180..1.180 rows=973 loops=1)
              Index Cond: ((zipcode)::text = '12345'::text)
Planning Time: 2.810 ms
JIT:
  Functions: 6
  Options: Inlining true, Optimization true, Expressions true, Deforming true
  Timing: Generation 3.133 ms, Inlining 81.749 ms, Optimization 27.432 ms, Emission 16.072 ms, Total 128.387 ms
Execution Time: 140.700 ms
*/

/*
In the first execution plan (Plan 1), the query uses a parallel sequential scan (Parallel Seq Scan) on the addresses table. It scans the entire table and applies a filter on the zipcode column to only return rows where the zipcode matches '12345'. However, this approach is not efficient as it needs to scan a large number of rows (33,333,009 rows) and then filter them, resulting in a high execution time of 3470.053 ms.

In the second execution plan (Plan 2), the query uses a parallel bitmap heap scan (Parallel Bitmap Heap Scan) on the addresses table. It first performs a bitmap index scan (Bitmap Index Scan) on the idx_addresses_zipcode index, which is specifically created for the zipcode column. This index scan quickly identifies the relevant rows based on the zipcode condition. Then, the bitmap heap scan retrieves the actual rows based on the bitmap, resulting in a much smaller number of rows (324 rows) to process. This approach is more efficient because it avoids scanning the entire table and performs targeted index-based retrieval, resulting in a significantly lower execution time of 140.700 ms.

In summary, the second execution plan (Plan 2) is a significant improvement over the first plan (Plan 1) in terms of execution time. It utilizes an index scan and bitmap heap scan combination to efficiently retrieve the desired rows based on the zipcode condition, resulting in a faster query execution.
*/


/*Why is the cost in second plan higher than the first ?

The cost values in the execution plans are not directly related to the execution time or performance of the query. Instead, they represent a relative estimation of the query's resource consumption, specifically the estimated amount of work and resources needed to execute each step of the plan.

In the first execution plan (Plan 1), the cost is shown as cost=1000.00..803451.98. This cost estimation suggests that the query execution involves a significant amount of work and resources. It is important to note that the cost value is an arbitrary unit and doesn't have a specific meaning in terms of time or memory usage.

In the second execution plan (Plan 2), the cost is shown as cost=15515.00..1235954.14. The higher cost in this plan compared to the first plan does not necessarily indicate that it is less efficient or slower. The cost estimation can vary based on factors such as the complexity of the query, the amount of data to be processed, and the specific optimization techniques used by the query planner.

In Plan 2, the use of a bitmap index scan allows the query planner to efficiently identify the relevant rows based on the zipcode condition. Although the cost value appears higher, the actual execution time is significantly lower, as indicated by the Execution Time value (140.700 ms). The cost estimate may reflect the additional work required for index scanning and bitmap operations, but these operations are more efficient for the specific query conditions.

In conclusion, the cost values in the execution plans provide an estimation of resource consumption but should not be directly interpreted as a measure of performance. The actual execution time and the number of rows processed are more meaningful indicators of query efficiency.


*/



