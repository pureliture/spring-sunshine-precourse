CREATE TABLE weather_code (
    code INT PRIMARY KEY,
    description VARCHAR(100) NOT NULL
);

INSERT INTO weather_code (code, description) VALUES
    (0, '맑음'),
    (1, '대체로 맑음'),
    (2, '부분적으로 흐림'),
    (3, '흐림'),
    (45, '안개'),
    (48, '결빙 안개'),
    (51, '옅은 이슬비'),
    (53, '중간 이슬비'),
    (55, '짙은 이슬비'),
    (61, '약한 비'),
    (63, '중간 비'),
    (65, '강한 비');
