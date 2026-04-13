-- ОЧИСТКА (важен порядок из-за FK)
DELETE FROM rental_car;
DELETE FROM rentals;
DELETE FROM cars;
DELETE FROM employees;
DELETE FROM clients;
DELETE FROM users;

-- USERS (с фиксированными ID)
INSERT INTO users (id, login, password, full_name, phone, role) VALUES
(2, 'manager1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.w1j2p4P4c.e4wYf4Hy', 'Петров Петр Петрович', '+79012345678', 'MANAGER'),
(3, 'employee1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.w1j2p4P4c.e4wYf4Hy', 'Сидоров Алексей Сергеевич', '+79023456789', 'MANAGER'),
(4, 'client1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.w1j2p4P4c.e4wYf4Hy', 'Козлов Николай Андреевич', '+79034567890', 'CLIENT'),
(5, 'client2', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.w1j2p4P4c.e4wYf4Hy', 'Смирнова Ольга Дмитриевна', '+79045678901', 'CLIENT'),
(6, 'client3', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.w1j2p4P4c.e4wYf4Hy', 'Волков Дмитрий Олегович', '+79056789012', 'CLIENT');

-- CLIENTS
INSERT INTO clients (id, driver_license, birth_date, rent_count, personal_email, user_id) VALUES
(1, '77 11 123456', '1990-05-15', 2, 'kozlov.n@mail.ru', 4),
(2, '77 22 234567', '1985-08-22', 5, 'smirnova.o@mail.ru', 5),
(3, '77 33 345678', '1995-12-03', 1, 'volkov.d@mail.ru', 6);

-- EMPLOYEES
INSERT INTO employees (id, position, salary, department, office_number, work_email, user_id) VALUES
(1, 'Генеральный директор', 150000.00, 'Управление', '101', 'director@carental.ru', 2),
(2, 'Менеджер по аренде', 80000.00, 'Аренда', '201', 'manager@carental.ru', 3);

-- CARS
INSERT INTO cars (id, brand, model, year, price, deposit, available, vin, registration_date, engine_volume, color, insurance_valid_until, inspection_valid_until) VALUES
(1, 'Toyota', 'Camry', 2022, 3500.00, 50000.00, TRUE, 'JTDKN3DU5A0123456', '2022-03-15', 2.50, 'Белый', '2026-03-15', '2026-03-15'),
(2, 'BMW', 'X5', 2023, 8500.00, 100000.00, TRUE, 'WBAJB31060B123456', '2023-06-20', 3.00, 'Черный', '2026-06-20', '2026-06-20'),
(3, 'Lada', 'Vesta', 2024, 2500.00, 30000.00, TRUE, 'XTA210740L0123456', '2024-01-10', 1.60, 'Серый', '2026-01-10', '2026-01-10');

-- RENTALS
INSERT INTO rentals (id, status, start_date, end_date, comment, client_id, employee_id) VALUES
(1, 'COMPLETED', '2025-01-10', '2025-01-15', 'Аренда на новогодние праздники', 1, 2),
(2, 'COMPLETED', '2025-02-20', '2025-02-25', 'Деловая поездка в Москву', 2, 2),
(3, 'ACTIVE', '2025-03-01', '2025-03-10', 'Отпуск на выходные', 3, 2);

-- RENTAL_CAR
INSERT INTO rental_car (car_id, rental_id, discount) VALUES
(1, 1, 0.00),
(2, 1, 10.00),
(2, 2, 5.00),
(3, 3, 0.00);


SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('clients_id_seq', (SELECT MAX(id) FROM clients));
SELECT setval('employees_id_seq', (SELECT MAX(id) FROM employees));
SELECT setval('cars_id_seq', (SELECT MAX(id) FROM cars));
SELECT setval('rentals_id_seq', (SELECT MAX(id) FROM rentals));