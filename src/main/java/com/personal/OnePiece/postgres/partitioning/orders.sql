CREATE TABLE orders (
    order_id SERIAL,
    order_date DATE,
    customer_id INT,
    product_id INT,
    quantity INT,
    amount DECIMAL(10, 2),
    primary key (order_id, order_date, customer_id)
) PARTITION BY RANGE (order_date, customer_id);