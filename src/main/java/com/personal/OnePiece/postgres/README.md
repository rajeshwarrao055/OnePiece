## Description

Adding PostgreSQL topics as a refresher

## Indexing
PostgreSQL offers various indexing strategies to optimize query performance.

The effectiveness of an index can vary depending on several factors, including the data distribution,
query conditions, and database statistics. In some scenarios, the query planner
may choose not to use an index due to factors such as the selectivity of the query or the table size.

Default indexing method : B-tree Indexes

* B-tree Indexes : Work well for most type of queries and are efficient for range scans
  * Suitable for columns that have a wide range of distinct values
  * typically used for equality and range queries
* Hash Indexes 