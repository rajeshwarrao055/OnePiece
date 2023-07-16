## Description

Adding PostgreSQL topics as a refresher

## Table Partitioning 
PostgreSQL supports table partitioning, which allows you to divide large tables into smaller, more manageable pieces. This can significantly improve query performance by reducing the amount of data scanned. You can partition tables based on a range, list, or hash partitioning strategy.

## Indexing
PostgreSQL offers various indexing strategies to optimize query performance. Along with traditional B-tree indexes, you can use advanced indexing techniques like GiST (Generalized Search Tree), GIN (Generalized Inverted Index), and BRIN (Block Range Index). Understanding when and how to use each type of index can greatly impact the efficiency of your queries.

The effectiveness of an index can vary depending on several factors, including the data distribution,
query conditions, and database statistics. In some scenarios, the query planner
may choose not to use an index due to factors such as the selectivity of the query or the table size.

Default indexing method : B-tree Indexes

* B-tree Indexes : Work well for most type of queries and are efficient for range scans
  * Suitable for columns that have a wide range of distinct values
  * typically used for equality and range queries
* Hash Indexes : Useful for equality based queries. Particularly efficient for exact matches
  * Not suitable for range queries
  * Perform well when index fits in memory and low likelihood of collisions
* GiST (Generalized search tree) indexes : Versatile and support a wide range of data types and search operations
  * Suitable for non-standard indexing requirements like spatial data, text search and tree like structures
  * can significantly improve query performance for complex search conditions
* GIN (Generalized Inverted Index) indexes : designed for full-text search and indexing arrays, composite types and other data structures
  * efficient for multi-valued and complex queries
  * can speed up search operations involving multiple values within a single column
* BRIN (Block Range Index) Indexes : Useful for large tables with sorted data and effective for range scans
  * Store Summary for each block of data , resulting in reduced index size
  * most suitable for columns with a high correlation between adjacent rows

## Advanced Query Optimization
PostgreSQL provides several techniques for optimizing complex queries. You can use features such as query rewriting, subquery optimization, common table expressions (CTEs), window functions, and advanced join strategies like hash joins and merge joins.

## Full-Text Search
PostgreSQL includes robust support for full-text search, enabling you to perform advanced text search operations efficiently. You can create full-text indexes, use powerful search operators and functions, and perform ranking and relevance-based searches.

## JSON and JSONB Data Types
PostgreSQL offers native support for storing and querying JSON data. The JSONB data type provides efficient storage and indexing for JSON documents. You can perform various operations on JSON data, such as indexing, querying, and modifying JSON documents directly in the database.

## Stored Procedures and Triggers
PostgreSQL supports the creation of stored procedures and triggers using procedural languages such as PL/pgSQL, PL/Python, PL/Perl, and more. Stored procedures allow you to encapsulate complex logic within the database, improving performance and maintainability.

## Replication and High Availability
PostgreSQL offers various replication mechanisms to achieve high availability and data redundancy. You can set up streaming replication, logical replication, or use third-party tools like Patroni or pgpool-II to manage replication and failover scenarios.

## Advanced Security Features
PostgreSQL provides several advanced security features, including SSL/TLS encryption, authentication mechanisms, fine-grained access control using roles and privileges, row-level security (RLS), and audit logging. Understanding and properly configuring these features are crucial for securing your PostgreSQL database.

## Performance Tuning and Monitoring
PostgreSQL provides various tools and techniques for performance tuning and monitoring. You can use the EXPLAIN command to analyze query execution plans, set configuration parameters for optimizing performance, and leverage tools like pg_stat_statements and pg_stat_monitor for detailed performance monitoring.

## Advanced Data Types and Extensions
PostgreSQL supports an extensive range of data types and allows you to create custom data types and extensions. Understanding how to use specialized data types like arrays, hstore, and geometric types can provide additional functionality and flexibility to your applications.

