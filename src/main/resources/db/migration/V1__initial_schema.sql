DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS rooms CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       user_id INTEGER UNIQUE NOT NULL,
                       balance INTEGER NOT NULL CHECK (balance >= 0),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rooms (
                       id BIGSERIAL PRIMARY KEY,
                       room_number INTEGER UNIQUE NOT NULL,
                       room_type VARCHAR(20) NOT NULL,
                       price_per_night INTEGER NOT NULL CHECK (price_per_night >= 0),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bookings (
                          id BIGSERIAL PRIMARY KEY,
                          user_id INTEGER NOT NULL,
                          user_balance_at_booking INTEGER NOT NULL,
                          room_number INTEGER NOT NULL,
                          room_type_at_booking VARCHAR(20) NOT NULL,
                          price_per_night_at_booking INTEGER NOT NULL,
                          check_in DATE NOT NULL,
                          check_out DATE NOT NULL,
                          total_cost INTEGER NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT check_dates CHECK (check_out > check_in)
);
CREATE INDEX idx_bookings_room_dates ON bookings(room_number, check_in, check_out);
CREATE INDEX idx_bookings_user ON bookings(user_id);
