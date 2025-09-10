CREATE TABLE users (
    id UUID PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE rooms (
    id UUID PRIMARY KEY,
    name TEXT UNIQUE NOT NULL,
    capacity INT,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE reservations (
    id UUID PRIMARY KEY,
    room_id UUID NOT NULL,
    user_id UUID NOT NULL,
    start_at TIMESTAMPTZ NOT NULL,
    end_at TIMESTAMPTZ NOT NULL,
    status TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES rooms (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT chk_time_slot CHECK (end_at > start_at)
);

CREATE INDEX idx_reservations_room_start ON reservations (room_id, start_at);
CREATE INDEX idx_reservations_user_start ON reservations (user_id, start_at);
