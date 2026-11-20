
CREATE DATABASE IF NOT EXISTS TlhohonolofatsoQoane2437485;
USE TlhohonolofatsoQoane2437485;

-- 2. Users Table for Role-Based Access Control
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('INSURANCE_OFFICER', 'CLAIMS_OFFICER', 'ADMINISTRATOR') NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- 3. Customers Table (Enforcing BR-C01 to BR-C04)
CREATE TABLE customers (
    customer_id VARCHAR(20) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    national_id VARCHAR(50) UNIQUE NOT NULL,
    date_of_birth DATE NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    physical_address TEXT NOT NULL
);

-- 4. Policies Table (Common policy fields)
CREATE TABLE policies (
    policy_number VARCHAR(30) PRIMARY KEY,
    customer_id VARCHAR(20) NOT NULL,
    policy_type ENUM('MOTOR', 'PROPERTY', 'LIFE', 'HEALTH') NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    coverage_amount DECIMAL(12,2) NOT NULL CHECK (coverage_amount > 0),
    annual_premium DECIMAL(10,2) NOT NULL,
    status ENUM('PENDING', 'ACTIVE', 'EXPIRED', 'CANCELLED') DEFAULT 'PENDING',
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- 5. Motor Policy Details Table
CREATE TABLE motor_policies (
    policy_number VARCHAR(30) PRIMARY KEY,
    registration_number VARCHAR(20) UNIQUE NOT NULL,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    manufacture_year INT NOT NULL,
    vehicle_value DECIMAL(12,2) NOT NULL,
    vehicle_type ENUM('Motorcycle', 'Private Car', 'Minibus', 'Truck') NOT NULL,
    cover_type ENUM('Third Party', 'Comprehensive') NOT NULL,
    FOREIGN KEY (policy_number) REFERENCES policies(policy_number) ON DELETE CASCADE
);

-- 6. Property Policy Details Table
CREATE TABLE property_policies (
    policy_number VARCHAR(30) PRIMARY KEY,
    property_address TEXT NOT NULL,
    property_type ENUM('Residential', 'Commercial') NOT NULL,
    property_value DECIMAL(12,2) NOT NULL,
    contents_value DECIMAL(12,2) DEFAULT 0.00,
    has_contents_cover BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (policy_number) REFERENCES policies(policy_number) ON DELETE CASCADE
);

-- 7. Life Policy Details Table
CREATE TABLE life_policies (
    policy_number VARCHAR(30) PRIMARY KEY,
    customer_age INT NOT NULL,
    occupation VARCHAR(100) NOT NULL,
    beneficiary_name VARCHAR(100) NOT NULL,
    beneficiary_contact VARCHAR(50) NOT NULL,
    FOREIGN KEY (policy_number) REFERENCES policies(policy_number) ON DELETE CASCADE
);

-- 8. Health Policy Details Table
CREATE TABLE health_policies (
    policy_number VARCHAR(30) PRIMARY KEY,
    health_plan ENUM('Basic', 'Standard', 'Premium') NOT NULL,
    number_of_dependants INT DEFAULT 0,
    FOREIGN KEY (policy_number) REFERENCES policies(policy_number) ON DELETE CASCADE
);

-- 9. Claims Table
CREATE TABLE claims (
    claim_number VARCHAR(30) PRIMARY KEY,
    policy_number VARCHAR(30) NOT NULL,
    customer_id VARCHAR(20) NOT NULL,
    date_submitted DATE NOT NULL,
    claim_amount DECIMAL(12,2) NOT NULL CHECK (claim_amount > 0),
    description TEXT NOT NULL,
    status ENUM('SUBMITTED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED', 'PAID') DEFAULT 'SUBMITTED',
    approved_amount DECIMAL(12,2) DEFAULT 0.00,
    rejection_reason TEXT,
    FOREIGN KEY (policy_number) REFERENCES policies(policy_number) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);


INSERT INTO users (username, password_hash, role, full_name, email) VALUES
('officer1', 'ubu123', 'INSURANCE_OFFICER', 'Tlhony Qoane', 'tlhoho@ubuntusure.co.ls'),
('claims1', 'ubu124', 'CLAIMS_OFFICER', 'Lineo Ramakatane', 'lineo@ubuntusure.co.ls'),

INSERT INTO users (username, password_hash, role, full_name, email) VALUES
('admin2', 'ubu126', 'ADMINISTRATOR', 'Mohau Mohau', 'admin@ubuntusure.co.ls');

INSERT INTO customers VALUES
('CUST-001', 'Lerato', 'Seema', 'ID123456789', '1990-05-12', 'Female', '+26658123456', 'lerato@gmail.com', '123 Main Road, Maseru');

INSERT INTO claims (claim_number, policy_number, customer_id, date_submitted, claim_amount, description, status) 
VALUES ('CLA 2345', 'POL-82663', 'CUST-56871', CURDATE(), 40000.00, 'vehicle accident', 'SUBMITTED');

SELECT customer_id, first_name, surname, national_id, email FROM customers;
DESCRIBE policies;
SELECT full_name, role FROM users;
DESCRIBE claims
ALTER TABLE users DROP COLUMN username;
 SELECT user_id, full_name, role FROM users;
