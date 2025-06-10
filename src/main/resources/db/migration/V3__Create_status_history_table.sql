-- Create status history sequence
CREATE SEQUENCE status_history_sequence_generator
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create status history table to track status changes
CREATE TABLE status_history (
                                id BIGINT PRIMARY KEY,
                                service_request_id BIGINT NOT NULL REFERENCES service_requests(id),
                                from_status VARCHAR(20),
                                to_status VARCHAR(20) NOT NULL,
                                changed_by VARCHAR(100) NOT NULL,
                                reason VARCHAR(255),
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add indexes for performance
CREATE INDEX idx_status_history_service_request_id ON status_history(service_request_id);
CREATE INDEX idx_status_history_to_status ON status_history(to_status);

-- Insert status history records for service request 1 (COMPLETED)
INSERT INTO status_history (id, service_request_id, from_status, to_status, changed_by, reason, created_at)
VALUES
    (nextval('status_history_sequence_generator'), 1, 'CREATED', 'ACCEPTED', 'Mike Johnson', 'Service advisor confirmation', CURRENT_TIMESTAMP - INTERVAL '7 days'),
    (nextval('status_history_sequence_generator'), 1, 'ACCEPTED', 'IN_PROGRESS', 'Sarah Williams', 'Service started', CURRENT_TIMESTAMP - INTERVAL '6 days'),
    (nextval('status_history_sequence_generator'), 1, 'IN_PROGRESS', 'REPAIR_IN_PROGRESS', 'Tom Wilson', 'Additional issues found', CURRENT_TIMESTAMP - INTERVAL '6 days'),
    (nextval('status_history_sequence_generator'), 1, 'REPAIR_IN_PROGRESS', 'COMPLETED', 'Tom Wilson', 'Service completed', CURRENT_TIMESTAMP - INTERVAL '5 days');

-- Insert status history records for service request 2 (IN_PROGRESS)
INSERT INTO status_history (id, service_request_id, from_status, to_status, changed_by, reason, created_at)
VALUES
    (nextval('status_history_sequence_generator'), 2, 'CREATED', 'ACCEPTED', 'Mike Johnson', 'Service advisor confirmation', CURRENT_TIMESTAMP - INTERVAL '3 days'),
    (nextval('status_history_sequence_generator'), 2, 'ACCEPTED', 'IN_PROGRESS', 'Rob Davis', 'Service started', CURRENT_TIMESTAMP - INTERVAL '1 day');

-- Insert status history records for service request 3 (REPAIR_IN_PROGRESS)
INSERT INTO status_history (id, service_request_id, from_status, to_status, changed_by, reason, created_at)
VALUES
    (nextval('status_history_sequence_generator'), 3, 'CREATED', 'ACCEPTED', 'Lisa Garcia', 'Service advisor confirmation', CURRENT_TIMESTAMP - INTERVAL '4 days'),
    (nextval('status_history_sequence_generator'), 3, 'ACCEPTED', 'IN_PROGRESS', 'David Chen', 'Service started', CURRENT_TIMESTAMP - INTERVAL '3 days'),
    (nextval('status_history_sequence_generator'), 3, 'IN_PROGRESS', 'REPAIR_IN_PROGRESS', 'David Chen', 'Issues identified', CURRENT_TIMESTAMP - INTERVAL '1 day');

-- Insert status history records for service request 4 (ACCEPTED)
INSERT INTO status_history (id, service_request_id, from_status, to_status, changed_by, reason, created_at)
VALUES
    (nextval('status_history_sequence_generator'), 4, 'CREATED', 'ACCEPTED', 'Mike Johnson', 'Service advisor confirmation', CURRENT_TIMESTAMP - INTERVAL '1 day');

-- Insert status history records for service request 5 (CREATED)
INSERT INTO status_history (id, service_request_id, from_status, to_status, changed_by, reason, created_at)
VALUES
    (nextval('status_history_sequence_generator'), 5, 'CREATED', 'CREATED', 'system', 'Initial creation', CURRENT_TIMESTAMP - INTERVAL '2 days');

-- Insert status history records for service request 6 (CREATED)
INSERT INTO status_history (id, service_request_id, from_status, to_status, changed_by, reason, created_at)
VALUES
    (nextval('status_history_sequence_generator'), 6, 'CREATED', 'CREATED', 'system', 'Initial creation', CURRENT_TIMESTAMP - INTERVAL '1 day');

-- Insert status history records for service request 7 (CANCELLED)
INSERT INTO status_history (id, service_request_id, from_status, to_status, changed_by, reason, created_at)
VALUES
    (nextval('status_history_sequence_generator'), 7, 'CREATED', 'ACCEPTED', 'Lisa Garcia', 'Service advisor confirmation', CURRENT_TIMESTAMP - INTERVAL '5 days'),
    (nextval('status_history_sequence_generator'), 7, 'ACCEPTED', 'CANCELLED', 'Mike Johnson', 'Customer request', CURRENT_TIMESTAMP - INTERVAL '2 days');

-- Insert status history records for service request 8 (COMPLETED)
INSERT INTO status_history (id, service_request_id, from_status, to_status, changed_by, reason, created_at)
VALUES
    (nextval('status_history_sequence_generator'), 8, 'CREATED', 'ACCEPTED', 'Mike Johnson', 'Service advisor confirmation', CURRENT_TIMESTAMP - INTERVAL '5 days'),
    (nextval('status_history_sequence_generator'), 8, 'ACCEPTED', 'IN_PROGRESS', 'Sarah Williams', 'Service started', CURRENT_TIMESTAMP - INTERVAL '4 days'),
    (nextval('status_history_sequence_generator'), 8, 'IN_PROGRESS', 'COMPLETED', 'Sarah Williams', 'Service completed', CURRENT_TIMESTAMP - INTERVAL '3 days');