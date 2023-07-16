CREATE TABLE orders (
  order_id SERIAL PRIMARY KEY,
  customer_id INT,
  order_date DATE,
  total_amount DECIMAL(10, 2)
);

WITH data AS (
  SELECT
    random() * 1000000 AS customer_id,
    NOW() - INTERVAL '1 year' * random() AS order_date,
    random() * 1000 AS total_amount
  FROM generate_series(1, 10000000) -- Number of inserts
)
INSERT INTO orders (customer_id, order_date, total_amount)
SELECT customer_id, order_date, total_amount
FROM data;