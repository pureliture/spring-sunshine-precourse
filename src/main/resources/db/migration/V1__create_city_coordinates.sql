CREATE TABLE city_coordinate (
    city_name VARCHAR(50) PRIMARY KEY,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
);

INSERT INTO city_coordinate (city_name, latitude, longitude) VALUES
    ('Seoul', 37.5665, 126.9780),
    ('Tokyo', 35.6895, 139.6917),
    ('NewYork', 40.7128, -74.0060),
    ('Paris', 48.8566, 2.3522),
    ('London', 51.5074, -0.1278),
    ('Sydney', -33.8688, 151.2093);
