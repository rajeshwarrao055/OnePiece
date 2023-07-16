## Description

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
