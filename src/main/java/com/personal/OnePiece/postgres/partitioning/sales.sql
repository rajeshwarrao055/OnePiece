CREATE TABLE sales (
    id SERIAL PRIMARY KEY,
    sale_date DATE,
    product_id INT,
    quantity INT,
    amount DECIMAL(10, 2)
);

-- Random data insertion into sales table
INSERT INTO sales (sale_date, product_id, quantity, amount)
SELECT
    NOW() - INTERVAL '1 day' * (random() * 365 * 4)::integer AS sale_date,
    (random() * 10 + 1)::integer AS product_id,
    (random() * 5 + 1)::integer AS quantity,
    (random() * 100)::numeric(10, 2) AS amount
FROM generate_series(1, 100);

-- create partitioned table
CREATE TABLE sales (
    id SERIAL PRIMARY KEY,
    sale_date DATE,
    product_id INT,
    quantity INT,
    amount DECIMAL(10, 2)
) PARTITION BY RANGE (sale_date);

-- in our case since table `sales` has already been created, need to create a separate parent table
create table sales_partitioned (like sales) partition by range(sale_date);

-- sales 2019
create table sales_2019 partition of sales_partitioned for values from ('2019-01-01') to ('2020-01-01');

-- sales 2020
create table sales_2020 partition of sales_partitioned for values from ('2020-01-01') to ('2021-01-01');

-- sales 2021
create table sales_2021 partition of sales_partitioned for values from ('2021-01-01') to ('2022-01-01');

-- sales 2022
create table sales_2022 partition of sales_partitioned for values from ('2022-01-01') to ('2023-01-01');

-- sales 2023
create table sales_2023 partition of sales_partitioned for values from ('2023-01-01') to ('2024-01-01');

select count(1) from sales_partitioned;
select count(1) from sales_2020 ;

explain
select * from sales_partitioned where sale_date >= '2022-01-01' and sale_date <= '2024-01-01';
-- above query uses specific partition according to sale_date mentioned in the where clause

--dropping a specific partition , impacts only data sitting in that partition. other partitions remain intact
drop table sales_2020;
select count(1) from sales_partitioned sp