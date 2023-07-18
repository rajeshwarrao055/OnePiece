-- retrieve statistics about index usage, no of index scans, tuples read and tuples fetched for each index
SELECT relname, indexrelname, idx_scan, idx_tup_read, idx_tup_fetch
FROM pg_stat_all_indexes WHERE schemaname = 'public' ORDER BY idx_scan DESC;

-- monitor active connections to the database
SELECT datname, usename, client_addr, application_name, backend_start, state, query
FROM pg_stat_activity WHERE state <> 'idle';

-- monitor table size and bloat
SELECT schemaname, relname, pg_size_pretty(pg_total_relation_size(relid)) AS total_size,
       pg_size_pretty(pg_total_relation_size(relid) - pg_relation_size(relid) - pg_indexes_size(relid) ) AS bloat_size
FROM pg_stat_all_tables WHERE schemaname = 'public' ORDER BY pg_total_relation_size(relid) DESC;

-- query to monitor buffer cache hit ratio : indicates how often postgres is able to retrieve data from cache
-- rather than reading from the disk
SELECT (sum(heap_blks_hit) - sum(heap_blks_read)) / NULLIF((sum(heap_blks_hit) + sum(heap_blks_read)),0) AS buffer_cache_hit_ratio
FROM pg_statio_user_tables;
