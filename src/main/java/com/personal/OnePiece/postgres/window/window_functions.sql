select order_id, customer_id, order_date, total_amount , row_number () over (order by total_amount desc) as row_number
from orders;

select order_id, customer_id, order_date, total_amount , rank () over (order by total_amount desc) as row_number
from orders;

select order_id, customer_id, order_date, total_amount , dense_rank () over (order by total_amount desc) as row_number
from orders;

SELECT order_id, customer_id, order_date,
       LEAD(order_date) OVER (PARTITION BY customer_id ORDER BY order_date) AS next_order_date,
       LAG(order_date) OVER (PARTITION BY customer_id ORDER BY order_date) AS previous_order_date
FROM orders;

SELECT order_id, customer_id, order_date, total_amount,
       FIRST_VALUE(total_amount) OVER (PARTITION BY customer_id ORDER BY order_date) AS first_total_amount,
       LAST_VALUE(total_amount) OVER (PARTITION BY customer_id ORDER BY order_date) AS last_total_amount
FROM orders;

SELECT order_id, customer_id, order_date, total_amount,
       SUM(total_amount) OVER (PARTITION BY customer_id) AS total_amount_sum,
       AVG(total_amount) OVER (PARTITION BY customer_id) AS total_amount_avg,
       MIN(total_amount) OVER (PARTITION BY customer_id) AS total_amount_min,
       MAX(total_amount) OVER (PARTITION BY customer_id) AS total_amount_max
FROM orders;