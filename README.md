# 날씨 조회 서비스 (Weather Service)

## 기능 목록
1. **도시 좌표 조회**: 입력된 도시 이름(Seoul, Tokyo, NewYork, Paris, London)에 해당하는 위도/경도를 조회한다.
2. **날씨 정보 조회**: Open-Meteo API를 사용하여 해당 좌표의 현재 날씨(온도, 체감 온도, 날씨 코드, 습도, 풍속)를 조회한다.
3. **날씨 요약 생성**: 조회된 날씨 정보를 바탕으로 사용자에게 친화적인 한 줄 요약 문장을 생성한다.
4. **API 응답**: 조회된 날씨 정보와 요약 문장을 JSON 형태로 반환한다.
5. **예외 처리**: 지원하지 않는 도시나 외부 API 오류 발생 시 정해진 포맷의 에러 응답을 반환한다.
6. **설정 외부화**: 도시 좌표 및 날씨 코드를 프로퍼티 파일로 분리하여 관리한다.
7. **회복 탄력성(Resilience)**: 외부 API 호출 시 타임아웃 및 재시도 기능을 통해 네트워크 불안정에 대비한다.

## 구현 전략
1. Jules, Codex 2개의 Sandboxed AI Coding Agent를 활용하여 초기 코드 개발
2. guideline 파일을 통해 Backend Architecture, 개발 규칙 등을 사전에 정의하고 Agent가 이를 참조하여 개발
3. 가능한 사전에 `build.gradle.kts`에 정의되어있던 의존성울 모두 사용하여 기능을 구현
4. Agent가 개발한 코드를 검토하고 필요 시 리팩토링 수행

## 구현 상세
* **아키텍처**: Domain Layer는 컴포넌트 기반, Service Layer가 Facade 역할로 조합하는 구조를 적용한다.
    * `api`: 컨트롤러 및 요청/응답 DTO (검증 및 위임).
    * `service`: Service Layer, Facade 역할로 여러 컴포넌트를 오케스트레이션.
    * `component`: 단일 책임을 가진 작은 컴포넌트들 (좌표 매핑, 요약 생성 등).
    * `domain`: 도메인 모델, 도메인 서비스.
    * `repository`: 도메인 리포지토리 포트 정의.
    * `infrastructure`: 외부 API 통신 (Open-Meteo Client) 등 기술 어댑터.
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
* **회복 탄력성**:
    * `RestClient` 설정 시 `HttpClient` 타임아웃(Connect, Response) 적용.
    * `ClientHttpRequestInterceptor`를 구현하여 실패 시 재시도 로직 적용 (Custom Retry Interceptor).


## 추가 요구사항
1. 조회한 날씨 데이터를 입력으로 LLM API를 호출해 요약을 생성하고 반환한다.
  - 설정에 따라 요약을 새로 생성하지 않고, 기존 요약을 재사용할 수 있다.
2. 도시는 물론, 권역 단위로도 날씨를 조회할 수 있다. 
  - e.g. 서울 전체, 수도권, 특정 구/동 등 
3. 날씨 또는 기온을 기준으로 복장을 추천한다.
  - e.g. 기온 구간, 강수 여부, 체감온도, 바람 등을 기준으로 추천 규칙을 적용한다. 
4. 각 요청에 대해 사용량과 비용 추정치를 로그로 남긴다. -> 모든 경우/info 정도 레벨 + DB에 저장까지
  - 예: 입력 토큰, 출력 토큰, 총 토큰, 모델명, 캐시 사용 여부, 추정 비용

## 구현 설계
1. `study` 패키지를 참고하여 `sunshine.infrastructure.ai` 패키지에 spring-ai ChatClientConfig 및 ChatClient(openai, genai)를 다형성(팩토리-메소드 패턴)을 적용하여 구현
  - Caffine Cache와 같은 인메모리 캐시를 사용하여 `key : 도시 + 날짜`, `value : 복장추천(LLM Output)` 형태로 캐싱하여, 캐시에 존재하는 경우 LLM API 호출안하게끔 효율화 
2. city 인풋을 받으면 City 도메인의 DB 조회 -> 없으면 https://nominatim.org/ API로 조회 -> 위경도 반환
3. 인풋 city의 위경도를 확보하면, 이를 인풋으로 weather 도메인의 날씨 조회 기능을 사용하여 날씨 조회(API Response DTO에 최대기온, 강수량, 체감온도 등의 컬럼 추가) -> LLM API에 이러한 인풋들을 모두 추가하여 추천
   - 복장 추천 프롬프트는 클래스로 별도로 구현하여 멤버로 파라미터, 프롬프트, 결과 등을 프롬프트 클래스에서 모두 처리
4. Slf4j info 레벨로 포맷팅된 로그 출력 추가 
  - DB에 llm 호출 이력 테이블 추가 -> spring-ai의 Response 클래스를 신규 테이블 entity로 변환 -> 저장 