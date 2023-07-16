-- Create a sample table without an index
CREATE TABLE articles (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100),
    tags VARCHAR(100)[]
);

-- Generate sample data with 10,000 rows
INSERT INTO articles (title, tags)
SELECT
    'Article ' || generate_series(1, 1000),
    ARRAY['tag' || floor(random() * 10)::INT]
FROM generate_series(1, 1000);

-- Execute a sample query without an index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM articles WHERE tags @> ARRAY['tag5'::varchar];

-- data pruned
/*
Seq Scan on articles  (cost=0.00..251985.12 rows=1172479 width=43) (actual time=4.058..1424.116 rows=1009568 loops=1)
  Filter: (tags @> '{tag5}'::character varying[])
  Rows Removed by Filter: 9100432
Planning Time: 0.097 ms
JIT:
  Functions: 2
  Options: Inlining false, Optimization false, Expressions true, Deforming true
  Timing: Generation 0.329 ms, Inlining 0.000 ms, Optimization 1.305 ms, Emission 2.685 ms, Total 4.319 ms
Execution Time: 1448.005 ms
*/

-- Create a GIN index on the "tags" column
CREATE INDEX idx_articles_tags ON articles USING GIN (tags);

-- Execute the same query with the index and measure the execution time
EXPLAIN ANALYZE SELECT * FROM articles WHERE tags @> ARRAY['tag5'::varchar];
/*
Bitmap Heap Scan on articles  (cost=9500.12..209647.48 rows=1030209 width=43) (actual time=90.885..937.217 rows=1009568 loops=1)
  Recheck Cond: (tags @> '{tag5}'::character varying[])
  Rows Removed by Index Recheck: 3180635
  Heap Blocks: exact=61456 lossy=33027
  ->  Bitmap Index Scan on idx_articles_tags  (cost=0.00..9242.57 rows=1030209 width=0) (actual time=81.933..81.933 rows=1009568 loops=1)
        Index Cond: (tags @> '{tag5}'::character varying[])
Planning Time: 0.290 ms
JIT:
  Functions: 2
  Options: Inlining false, Optimization false, Expressions true, Deforming true
  Timing: Generation 0.365 ms, Inlining 0.000 ms, Optimization 0.166 ms, Emission 1.187 ms, Total 1.718 ms
Execution Time: 961.064 ms
*/

-- NOTE : With a much larger data set ~9.1 GB , index size around 2.1 GB , query performance was degraded with usage of
-- GIN Index. Without Index , execution time ~13 seconds, with index ~17 seconds . Further notes below
/*
If you're experiencing degraded query performance after creating a GIN (Generalized Inverted Index) index in PostgreSQL, there could be several reasons for this. Here are a few possible explanations and suggestions to improve the situation:

Increased index size: GIN indexes are generally larger than traditional B-tree indexes. If the size of the GIN index is significantly larger than the previous index or if it doesn't fit entirely in memory, it can lead to increased disk I/O and slower query performance. In such cases, consider increasing the available memory or reviewing your indexing strategy.

Inefficient query plans: The query planner in PostgreSQL may not always choose the most efficient query plan when using a GIN index. This can occur if the statistics on the indexed column are outdated or if the planner's cost estimates are inaccurate. Try running ANALYZE on the table to update the statistics and see if it improves the query plan.

Ineffective index usage: Ensure that the queries you're running are actually able to utilize the GIN index. GIN indexes are particularly useful for searching arrays, composite types, and full-text search. If your queries don't involve these operations, the GIN index may not provide any benefit and could potentially slow down the queries.

Too many indexed columns: GIN indexes can be created on multiple columns, but keep in mind that each additional indexed column adds overhead to the index maintenance and storage. If you have too many indexed columns in the GIN index, it can negatively impact performance. Consider evaluating if all the indexed columns are necessary and try reducing the number of indexed columns if possible.

Over-indexing: Creating too many indexes on a table can lead to performance degradation due to the overhead of maintaining the indexes during data modifications (inserts, updates, deletes). If you have other indexes on the same table along with the GIN index, review the necessity of each index and consider removing any redundant or unused indexes.

Hardware limitations: It's worth considering if the degradation in performance is due to hardware limitations, such as insufficient CPU, memory, or disk I/O capacity. Ensure that your hardware resources are appropriately provisioned to handle the workload.
*/