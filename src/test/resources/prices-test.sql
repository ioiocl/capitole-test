DROP TABLE IF EXISTS PRICES;

CREATE TABLE PRICES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    priority INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    curr VARCHAR(3) NOT NULL
);

INSERT INTO PRICES (brand_id, start_date, end_date, price_list, product_id, priority, price, curr) VALUES
-- Datos originales
(1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
(1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
(1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),
(1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR'),

-- Nuevos datos de prueba
(1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 5, 35456, 0, 45.50, 'EUR'),
(1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 6, 35456, 1, 35.45, 'EUR'),
(2, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 7, 35455, 0, 55.50, 'EUR'),
(2, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 8, 35456, 1, 40.50, 'EUR'),
(2, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 9, 35455, 1, 58.95, 'EUR');
