DROP TABLE IF EXISTS rental_car;
DROP TABLE IF EXISTS rentals;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    full_name VARCHAR(255),
    phone VARCHAR(50),
    role VARCHAR(50)
);

CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    driver_license VARCHAR(255),
    birth_date DATE,
    rent_count INT,
    personal_email VARCHAR(255),
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    position VARCHAR(255),
    salary DOUBLE PRECISION,
    department VARCHAR(255),
    office_number VARCHAR(50),
    work_email VARCHAR(255),
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE cars (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(255),
    model VARCHAR(255),
    year INT,
    price DOUBLE PRECISION,
    deposit DOUBLE PRECISION,
    available BOOLEAN,
    vin VARCHAR(255),
    registration_date DATE,
    engine_volume DECIMAL(10,2),
    color VARCHAR(100),
    insurance_valid_until DATE,
    inspection_valid_until DATE
);

CREATE TABLE rentals (
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(50),
    start_date DATE,
    end_date DATE,
    comment TEXT,
    client_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE rental_car (
    car_id BIGINT NOT NULL,
    rental_id BIGINT NOT NULL,
    discount DOUBLE PRECISION,
    PRIMARY KEY (car_id, rental_id),
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (rental_id) REFERENCES rentals(id)
);