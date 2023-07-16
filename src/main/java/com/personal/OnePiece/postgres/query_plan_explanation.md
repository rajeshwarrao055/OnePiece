## Query Plan Explanation

Query Plan :
```
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
```

## Breakdown
* `Gather  (cost=28226.40..1457335.85 rows=1996667 width=17) (actual time=161.412..3040.419 rows=1998092 loops=1)`
-> overall cost estimate for executing the query. Cost represents an arbitrary unit used by PostgreSQL query optimizer 
to compare different execution plans. Lower cost generally indicates more efficient plan

* `Gather` node indicates that query will be executed in parallel using multiple workers 
```
  Workers Planned: 2
  Workers Launched: 2
```

* `Parallel BitMap Heap Scan` node represents main operation for retrieving the data. Performs a scan on the users table
using a bitmap index

* `Recheck Cond` indicates that the bitmap index scan performs a recheck on the retrieved rows to ensure the where clause condition.
During recheck, some rows are removed. mentioned in `Rows Removed by Index Recheck`
* `Heap Blocks` information shows number of blocks accessed from table's underlying storages. `exact=15829` blocks were
accessed precisely, while `lossy=183666` blocks were accessed using lossy compression
* `Bitmap Index Scan` node represents scan on index `idx_users_age` to retrieve matching rows. Index condition to filter results
* `Planning time` indicates time taken by query planner to generate the query 
* `JIT` section provides information about the JIT compilation of functions used in the query
* `Execution Time` -> total time taken to execute the query