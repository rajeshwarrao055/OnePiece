## Description

* Window functions allow to perform calculations across a set of rows in a query result.
* They are particularly useful for tasks like ranking, partitioning and generating running totals.

### ROW_NUMBER
This function assigns a unique integer value to each row within a window partition.

```postgres-sql
select order_id, customer_id, order_date, total_amount , row_number () over (order by total_amount desc) as row_number 
from orders;
```

## RANK & DENSE_RANK
This function assigns a rank to each row within a window partition, with RANK leaving gaps for ties
and DENSE_RANK providing consecutive ranks

```postgres-sql
select order_id, customer_id, order_date, total_amount , rank () over (order by total_amount desc) as row_number 
from orders;
```
Above if total_amount is same for two rows their rank will be same and then next amount will have a corresponding gap.
Dense rank won't have the gap

```postgres-sql

select order_id, customer_id, order_date, total_amount , dense_rank () over (order by total_amount desc) as row_number 
from orders;
```

so next amount will start with rank 2 instead of (count of rank=1 + 1).

## LEAD and LAG
LEAD -> allows to access the values of the next row within a window partition
LAG -> allows to access the values of the previous row within a window partition

```postgres-sql
SELECT order_id, customer_id, order_date,
       LEAD(order_date) OVER (PARTITION BY customer_id ORDER BY order_date) AS next_order_date,
       LAG(order_date) OVER (PARTITION BY customer_id ORDER BY order_date) AS previous_order_date
FROM orders;
```

## FIRST_VALUE and LAST_VALUE
These functions retrieve the first or last value within a window partition

```postgres-sql
SELECT order_id, customer_id, order_date, total_amount,
       FIRST_VALUE(total_amount) OVER (PARTITION BY customer_id ORDER BY order_date) AS first_total_amount,
       LAST_VALUE(total_amount) OVER (PARTITION BY customer_id ORDER BY order_date) AS last_total_amount
FROM orders;
```

## SUM,AVG, MIN, MAX
```postgres-sql
SELECT order_id, customer_id, order_date, total_amount,
       SUM(total_amount) OVER (PARTITION BY customer_id) AS total_amount_sum,
       AVG(total_amount) OVER (PARTITION BY customer_id) AS total_amount_avg,
       MIN(total_amount) OVER (PARTITION BY customer_id) AS total_amount_min,
       MAX(total_amount) OVER (PARTITION BY customer_id) AS total_amount_max
FROM orders;
```