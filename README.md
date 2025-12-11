# 날씨 조회 서비스 (Weather Service)

## 기능 목록
1. **도시 좌표 조회**: 입력된 도시 이름(Seoul, Tokyo, NewYork, Paris, London)에 해당하는 위도/경도를 조회한다.
2. **날씨 정보 조회**: Open-Meteo API를 사용하여 해당 좌표의 현재 날씨(온도, 체감 온도, 날씨 코드, 습도, 풍속)를 조회한다.
3. **날씨 요약 생성**: 조회된 날씨 정보를 바탕으로 사용자에게 친화적인 한 줄 요약 문장을 생성한다.
4. **API 응답**: 조회된 날씨 정보와 요약 문장을 JSON 형태로 반환한다.
5. **예외 처리**: 지원하지 않는 도시나 외부 API 오류 발생 시 정해진 포맷의 에러 응답을 반환한다.
6. **설정 외부화**: 도시 좌표 및 날씨 코드를 프로퍼티 파일로 분리하여 관리한다.

## 구현 전략
* **아키텍처**: 가이드라인에 따라 CBD(Component Based Development) + Facade 패턴을 적용한다.
    * `api`: 컨트롤러 및 요청/응답 DTO (검증 및 위임).
    * `application`: Facade (여러 컴포넌트를 조합하여 비즈니스 로직 수행).
    * `component`: 단일 책임을 가진 작은 컴포넌트들 (좌표 매핑, 요약 생성 등).
    * `infrastructure`: 외부 API 통신 (Open-Meteo Client).
* **제약 사항 준수**:
    * `else`, `switch`, `ternary operator` 사용 금지.
    * 들여쓰기 2단계 제한.
    * 메서드 길이 15줄 제한.
    * Google Java Style Guide (4 spaces indent).
* **No-Else/Switch 전략**:
    * 도시 매핑: `Map<String, Coordinate>` 자료구조를 활용하여 조건문 없이 O(1) 조회.
    * 날씨 코드 매핑: `Map<Integer, String>` 활용.
    * 방어적 코딩(Guard Clauses)을 통해 `else` 제거.
* **설정 관리**:
    * `CityProperties`, `WeatherProperties` 클래스를 통해 외부 설정(YAML)을 객체로 바인딩하여 사용.
