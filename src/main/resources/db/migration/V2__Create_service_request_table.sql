-- Create service request sequence
CREATE SEQUENCE service_request_sequence_generator
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create service requests table
CREATE TABLE service_requests (
                                  id BIGINT PRIMARY KEY,
                                  request_number VARCHAR(20) NOT NULL UNIQUE,
                                  description TEXT,
                                  car_model VARCHAR(100) NOT NULL,
                                  car_year VARCHAR(10) NOT NULL,
                                  license_plate VARCHAR(20) NOT NULL,
                                  status VARCHAR(20) NOT NULL,
                                  client_id BIGINT NOT NULL REFERENCES clients(id),
                                  estimated_cost VARCHAR(50),
                                  actual_cost VARCHAR(50),
                                  estimated_completion_date TIMESTAMP,
                                  actual_completion_date TIMESTAMP,
                                  mechanic_notes TEXT,
                                  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add indexes for performance
CREATE INDEX idx_service_requests_client_id ON service_requests(client_id);
CREATE INDEX idx_service_requests_status ON service_requests(status);
CREATE INDEX idx_service_requests_request_number ON service_requests(request_number);

-- Insert test service requests with meaningful data
INSERT INTO service_requests (
    id, request_number, description, car_model, car_year, license_plate,
    status, client_id, estimated_cost, estimated_completion_date
)
VALUES
    (
        nextval('service_request_sequence_generator'),
        'SRQ-001',
        'Regular oil change and filter replacement. Check fluid levels.',
        'Toyota Camry',
        '2020',
        'ABC-1234',
        'COMPLETED',
        1,
        '$65.00',
        CURRENT_TIMESTAMP - INTERVAL '5 days'
    ),
    (
        nextval('service_request_sequence_generator'),
        'SRQ-002',
        'Brake pad replacement on all wheels. Inspect rotors.',
        'Honda Accord',
        '2019',
        'DEF-5678',
        'IN_PROGRESS',
        2,
        '$320.00',
        CURRENT_TIMESTAMP + INTERVAL '1 day'
    ),
    (
        nextval('service_request_sequence_generator'),
        'SRQ-003',
        'Check engine light on. Diagnostic scan needed. Customer reports rough idling.',
        'Ford F-150',
        '2018',
        'GHI-9012',
        'REPAIR_IN_PROGRESS',
        3,
        '$150.00',
        CURRENT_TIMESTAMP + INTERVAL '2 days'
    ),
    (
        nextval('service_request_sequence_generator'),
        'SRQ-004',
        'Full transmission service. Customer reports delayed shifting.',
        'Chevrolet Malibu',
        '2017',
        'JKL-3456',
        'ACCEPTED',
        4,
        '$450.00',
        CURRENT_TIMESTAMP + INTERVAL '4 days'
    ),
    (
        nextval('service_request_sequence_generator'),
        'SRQ-005',
        'Annual inspection and maintenance service. Check all systems.',
        'Nissan Altima',
        '2021',
        'MNO-7890',
        'CREATED',
        5,
        '$120.00',
        CURRENT_TIMESTAMP + INTERVAL '7 days'
    ),
    (
        nextval('service_request_sequence_generator'),
        'SRQ-006',
        'Air conditioning not working. Check compressor and refrigerant levels.',
        'Hyundai Sonata',
        '2022',
        'PQR-1234',
        'CREATED',
        6,
        '$280.00',
        CURRENT_TIMESTAMP + INTERVAL '3 days'
    ),
    (
        nextval('service_request_sequence_generator'),
        'SRQ-007',
        'Tire rotation and wheel alignment. Customer reports vehicle pulling to the right.',
        'Kia Sorento',
        '2020',
        'STU-5678',
        'CANCELLED',
        7,
        '$95.00',
        CURRENT_TIMESTAMP - INTERVAL '1 day'
    ),
    (
        nextval('service_request_sequence_generator'),
        'SRQ-008',
        'Battery replacement. Vehicle not starting consistently.',
        'Mazda CX-5',
        '2019',
        'VWX-9012',
        'COMPLETED',
        8,
        '$180.00',
        CURRENT_TIMESTAMP - INTERVAL '3 days'
    );