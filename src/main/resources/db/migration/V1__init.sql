CREATE TABLE country (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE city (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    country_id BIGINT,
    FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE weather_code (
    code INT PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

-- 초기 데이터 삽입
INSERT INTO country (name) VALUES ('South Korea');
INSERT INTO country (name) VALUES ('Japan');
INSERT INTO country (name) VALUES ('USA');
INSERT INTO country (name) VALUES ('France');
INSERT INTO country (name) VALUES ('UK');

-- 서울 (South Korea)
INSERT INTO city (name, latitude, longitude, country_id)
SELECT 'seoul', 37.5665, 126.9780, id FROM country WHERE name = 'South Korea';

-- 도쿄 (Japan)
INSERT INTO city (name, latitude, longitude, country_id)
SELECT 'tokyo', 35.6895, 139.6917, id FROM country WHERE name = 'Japan';

-- 뉴욕 (USA)
INSERT INTO city (name, latitude, longitude, country_id)
SELECT 'newyork', 40.7128, -74.0060, id FROM country WHERE name = 'USA';

-- 파리 (France)
INSERT INTO city (name, latitude, longitude, country_id)
SELECT 'paris', 48.8566, 2.3522, id FROM country WHERE name = 'France';

-- 런던 (UK)
INSERT INTO city (name, latitude, longitude, country_id)
SELECT 'london', 51.5074, -0.1278, id FROM country WHERE name = 'UK';

-- Weather Codes
INSERT INTO weather_code (code, description) VALUES (0, '맑음');
INSERT INTO weather_code (code, description) VALUES (1, '대체로 맑음');
INSERT INTO weather_code (code, description) VALUES (2, '약간 흐림');
INSERT INTO weather_code (code, description) VALUES (3, '흐림');
INSERT INTO weather_code (code, description) VALUES (45, '안개');
INSERT INTO weather_code (code, description) VALUES (48, '안개');
INSERT INTO weather_code (code, description) VALUES (51, '이슬비');
INSERT INTO weather_code (code, description) VALUES (53, '이슬비');
INSERT INTO weather_code (code, description) VALUES (55, '이슬비');
INSERT INTO weather_code (code, description) VALUES (61, '비');
INSERT INTO weather_code (code, description) VALUES (63, '비');
INSERT INTO weather_code (code, description) VALUES (65, '비');
INSERT INTO weather_code (code, description) VALUES (71, '눈');
INSERT INTO weather_code (code, description) VALUES (73, '눈');
INSERT INTO weather_code (code, description) VALUES (75, '눈');
INSERT INTO weather_code (code, description) VALUES (80, '소나기');
INSERT INTO weather_code (code, description) VALUES (81, '소나기');
INSERT INTO weather_code (code, description) VALUES (82, '소나기');
INSERT INTO weather_code (code, description) VALUES (95, '천둥번개');
INSERT INTO weather_code (code, description) VALUES (96, '천둥번개');
INSERT INTO weather_code (code, description) VALUES (99, '천둥번개');
