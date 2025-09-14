CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE rooms (
    id UUID PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    capacity INT,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE reservations (
    id UUID PRIMARY KEY,
    room_id UUID NOT NULL,
    user_id UUID NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES rooms (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT chk_time_slot CHECK (end_at > start_at)
);

CREATE INDEX idx_reservations_room_start ON reservations (room_id, start_at);
CREATE INDEX idx_reservations_user_start ON reservations (user_id, start_at);
