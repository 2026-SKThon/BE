# On-e 백엔드

아이 체온 모니터링 앱 **On-e**의 Spring Boot 백엔드 서버입니다.

---

## 1. Git Convention

### 1.1 브랜치 전략

| 브랜치                                     | 용도                        |
|-----------------------------------------|---------------------------|
| `main`                                  | 배포 가능한 최종 브랜치             |
| `develop`                               | 개발 통합 브랜치                 |
| `feature/{issue-number}-{feature-name}` | 기능 개발 브랜치                 |
| `fix/{issue-number}-{bug-name}`         | 버그 수정 브랜치                 |
| `refactor/{issue-number}-{target}`      | 리팩토링 브랜치                  |
| `docs/{issue-number}-{doc-name}`        | 문서 수정 브랜치                 |
| `chore/{issue-number}-{task-name}`      | 설정, 빌드, 기타 작업 브랜치         |

### 1.2 브랜치 예시

```text
feature/12-child-register
feature/23-temperature-record
fix/31-device-connect
refactor/42-hospital-service
docs/50-api-spec
chore/61-swagger-config
```

### 1.3 작업 흐름

1. GitHub Issue를 생성한다.
2. `develop` 브랜치에서 작업 브랜치를 생성한다.
3. 기능 구현 후 로컬에서 테스트와 빌드를 확인한다.
4. 작업 브랜치를 원격 저장소에 push한다.
5. `develop` 브랜치로 Pull Request를 생성한다.
6. CI 빌드가 성공하면 PR을 merge한다.
7. 데모 또는 배포 시점에 `develop`에서 `main`으로 merge한다.

### 1.4 브랜치 삭제 규칙

- `main`, `develop` 브랜치는 삭제하지 않는다.
- `feature/*`, `fix/*`, `refactor/*`, `docs/*`, `chore/*` 브랜치는 PR이 `develop`에 merge된 후 삭제한다.
- merge가 완료된 원격 브랜치는 PR 작성자가 삭제한다.
- 로컬 브랜치는 각자 작업자가 정리한다.

```bash
git branch -d feature/12-child-register
git fetch --prune
```

---

## 2. Commit Convention

### 2.1 커밋 메시지 형식

```text
type(scope): subject
```

### 2.2 type 규칙

| type       | 의미                                          |
|------------|---------------------------------------------|
| `feat`     | 새로운 기능 추가                                   |
| `fix`      | 버그 수정                                       |
| `docs`     | 문서 수정                                       |
| `style`    | 코드 포맷팅, 세미콜론, 공백 등 기능 변경 없는 수정             |
| `refactor` | 리팩토링                                        |
| `test`     | 테스트 코드 추가 또는 수정                             |
| `chore`    | 빌드, 설정, 패키지 관리 등 기타 작업                     |
| `perf`     | 성능 개선                                       |
| `ci`       | CI/CD 설정 수정                                 |
| `rename`   | 파일명 또는 폴더명 변경                               |
| `remove`   | 파일 또는 코드 삭제                                 |

### 2.3 커밋 예시

```text
feat(child): 아이 등록 API 추가
feat(temperature): 체온 기록 저장 API 추가
fix(device): 디바이스 연결 상태 검증 로직 수정
docs(api): API 명세서 병원 영역 수정
refactor(hospital): 병원 조회 서비스 로직 분리
test(temperature): 체온 이상 판단 로직 테스트 추가
chore(config): Swagger 설정 추가
ci(deploy): GitHub Actions 배포 스크립트 수정
```

---

## 3. Code Style Convention

### 3.1 네이밍 규칙

| 구분           | 규칙               | 예시                                       |
|--------------|------------------|------------------------------------------|
| 클래스명         | PascalCase       | `ChildService`, `TemperatureController`  |
| 메서드명         | camelCase        | `recordTemperature`, `findMyChildren`    |
| 변수명          | camelCase        | `childId`, `deviceStatus`               |
| 상수명          | UPPER_SNAKE_CASE | `MAX_FEVER_THRESHOLD`                   |
| 패키지명         | lowercase        | `com.one.domain.temperature`            |
| DB 테이블명      | snake_case       | `temperature_records`                   |
| DB 컬럼명       | snake_case       | `child_id`, `measured_at`               |
| API URL      | 복수형 리소스 중심       | `/children/{childId}/temperatures`      |

### 3.2 패키지 구조

```text
com.one
├─ domain
│   ├─ user
│   │   ├─ controller
│   │   ├─ dto
│   │   ├─ entity
│   │   ├─ enums
│   │   ├─ exception
│   │   ├─ repository
│   │   ├─ service
│   │   └─ mapper
│   ├─ child
│   │   ├─ controller
│   │   ├─ dto
│   │   ├─ entity
│   │   ├─ enums
│   │   ├─ exception
│   │   ├─ repository
│   │   ├─ service
│   │   └─ mapper
│   ├─ device
│   │   └─ (동일 구조)
│   ├─ temperature
│   │   └─ (동일 구조)
│   └─ hospital
│       └─ (동일 구조)
└─ global
    ├─ config        # CORS, Swagger, QueryDSL 설정
    ├─ entity        # BaseTimeEntity
    ├─ exception     # ErrorCode, CustomException, GlobalExceptionHandler
    ├─ response      # BaseResponse, PageResponse
    ├─ resolver      # CurrentUserProvider (X-USER-ID 헤더)
    └─ util          # HealthCheckController
```

각 도메인은 `controller`, `dto`, `entity`, `enums`, `exception`, `repository`, `service`, `mapper` 패키지 구조를 따른다.

### 3.3 계층별 역할

| 계층           | 역할                                           |
|--------------|----------------------------------------------|
| Controller   | 요청/응답 처리, 인증 사용자 전달, 비즈니스 로직 작성 금지           |
| Service      | 비즈니스 로직, 권한 검증, 트랜잭션 처리                      |
| Repository   | DB 접근                                        |
| Entity       | DB 테이블 매핑                                    |
| DTO          | 요청/응답 데이터 전달 (Request / Response 분리)         |
| Enums        | 도메인 내 상태값 및 타입 정의                            |
| Exception    | 도메인별 에러 코드 정의                                |
| Mapper       | Entity ↔ DTO 변환                              |

### 3.4 코드 작성 규칙

- Controller에서는 비즈니스 로직을 작성하지 않는다.
- Entity를 API 응답으로 직접 반환하지 않는다.
- Request DTO와 Response DTO를 분리한다.
- 인증된 사용자는 `X-USER-ID` 헤더에서 추출한 `currentUserId`를 사용한다.
- 내 정보, 내 아이, 내 디바이스 조회에는 `userId`를 요청값으로 받지 않는다.
- Path Variable로 받은 리소스 ID는 반드시 권한 검증을 수행한다.
- 상태값은 문자열 하드코딩 대신 Enum을 사용한다.
- 체온 상태 변경, 디바이스 연결처럼 상태 변경이 있는 기능은 `@Transactional`을 사용한다.
- 예외는 공통 예외 객체(`CustomException`)와 에러 코드(`ErrorCode`)를 사용한다.
- 모든 API 응답은 공통 응답 형식(`BaseResponse`)을 사용한다.

---

## 4. PR Convention

### 4.1 PR 제목 형식

```text
[type] 작업 내용 요약
```

예시

```text
[feat] 아이 등록 API 구현
[fix] 체온 기록 저장 오류 수정
[docs] API 명세서 병원 영역 수정
[chore] GitHub Actions 배포 설정 추가
```

### 4.2 PR 본문 포함 항목

```text
## 작업 내용
- 

## 변경된 API
- 

## 테스트 결과
- 

## 확인 필요 사항
- 
```

### 4.3 리뷰 코멘트 태그

| 태그      | 의미                  |
|---------|---------------------|
| `[필수]`  | 반드시 수정해야 하는 내용      |
| `[제안]`  | 더 나은 방향을 제안하는 내용    |
| `[질문]`  | 의도를 확인하기 위한 질문      |
| `[공유]`  | 참고하면 좋은 정보          |
| `[칭찬]`  | 좋은 구현에 대한 피드백       |

리뷰 코멘트 예시

```text
[필수] 이 API는 본인 아이인지 확인하는 권한 검증이 필요합니다.

[제안] 체온 상태 판단 로직은 TemperatureStatus enum 내부 메서드로 분리하면 좋을 것 같습니다.

[질문] 디바이스는 아이 1명에 1개만 연결 가능한 정책인데, 중복 연결 검증은 어디에서 처리되나요?

[공유] 이 부분은 추후 아두이노 실시간 수신 로직과 연결될 수 있어서 서비스 계층에 두는 것이 좋아 보입니다.

[칭찬] 예외 케이스가 명확하게 분리되어 있어서 읽기 좋습니다.
```

### 4.4 Merge 조건

- `main`, `develop` 브랜치에 직접 push하지 않는다.
- 빌드가 성공해야 한다.
- 충돌은 PR 작성자가 해결한다.
- API 변경 시 Swagger 또는 API 명세서를 함께 수정한다.
- DB 구조 변경 시 Flyway 마이그레이션 파일을 함께 추가한다.
- 공통 응답, 예외 처리, 인증 방식에 영향을 주는 변경은 팀원에게 공유한다.

---

## 5. 로컬 개발 환경 설정

### 5.1 사전 요구사항

- Java 21
- Docker & Docker Compose

### 5.2 실행 방법

```bash
# 저장소 clone
git clone {repository-url}
cd backend

# 환경변수 파일 생성
cp .env.example .env
# .env 파일에 DB_PASSWORD 입력

# MySQL 컨테이너 실행
docker-compose up -d mysql

# 애플리케이션 실행
./gradlew bootRun
```

### 5.3 환경변수 목록

| 변수명                    | 설명                   | 예시                                 |
|------------------------|----------------------|------------------------------------|
| `DB_URL`               | DB 접속 URL            | `jdbc:mysql://localhost:3306/on_e` |
| `DB_USERNAME`          | DB 접속 계정             | `root`                             |
| `DB_PASSWORD`          | DB 비밀번호              | `password123`                      |
| `CORS_ALLOWED_ORIGINS` | 허용할 프론트엔드 Origin 목록  | `http://localhost:3000`            |

### 5.4 헬스 체크

```bash
curl http://localhost:8080/health
# 응답: {"isSuccess":true,"code":"200","message":"요청에 성공하였습니다.","result":"ok"}
```
