-- Create client sequence
CREATE SEQUENCE client_sequence_generator
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create clients table
CREATE TABLE clients (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add index for email search
CREATE INDEX idx_clients_email ON clients(email);

-- Insert test clients with meaningful data
INSERT INTO clients (id, name, email, phone_number)
VALUES 
    (nextval('client_sequence_generator'), 'John Smith', 'john.smith@example.com', '+1-555-123-4567'),
    (nextval('client_sequence_generator'), 'Emma Johnson', 'emma.johnson@example.com', '+1-555-234-5678'),
    (nextval('client_sequence_generator'), 'Michael Brown', 'michael.brown@example.com', '+1-555-345-6789'),
    (nextval('client_sequence_generator'), 'Sophia Wilson', 'sophia.wilson@example.com', '+1-555-456-7890'),
    (nextval('client_sequence_generator'), 'James Taylor', 'james.taylor@example.com', '+1-555-567-8901'),
    (nextval('client_sequence_generator'), 'Olivia Davis', 'olivia.davis@example.com', '+1-555-678-9012'),
    (nextval('client_sequence_generator'), 'William Martinez', 'william.martinez@example.com', '+1-555-789-0123'),
    (nextval('client_sequence_generator'), 'Ava Anderson', 'ava.anderson@example.com', '+1-555-890-1234'),
    (nextval('client_sequence_generator'), 'Alexander Thomas', 'alexander.thomas@example.com', '+1-555-901-2345'),
    (nextval('client_sequence_generator'), 'Isabella Jackson', 'isabella.jackson@example.com', '+1-555-012-3456'); 