# Spring State Machine 주문 관리 시스템

## 프로젝트 개요

이 프로젝트는 Spring State Machine을 사용하여 주문 상태를 관리하는 웹 애플리케이션입니다. 주문의 생명주기를 상태 머신으로 모델링하여 체계적이고 안전한 상태 전환을 제공합니다.

## 기술 스택

-   **Java**: 25
-   **Spring Boot**: 4.1.1 (Spring Framework 7)
-   **Spring State Machine**: 4.0.2
-   **Spring Data JPA 4.x / Hibernate ORM 7.4**: 데이터 persistence (Jakarta Persistence)
-   **H2 Database**: 인메모리 데이터베이스 (MySQL 호환 모드)
-   **Lombok**: 코드 간소화
-   **Maven**: 3.9.16 (Maven Wrapper 3.3.4 포함)

## 주문 상태 플로우

```
NEW_ORDER → ORDER_APPROVED → DELIVERY_START → DELIVERY_END → COMPLETE
    ↓
ORDER_CANCEL
```

### 상태 설명

-   **NEW_ORDER**: 새로운 주문이 생성된 상태
-   **ORDER_APPROVED**: 주문이 승인된 상태
-   **ORDER_CANCEL**: 주문이 취소된 상태
-   **DELIVERY_START**: 배송이 시작된 상태
-   **DELIVERY_END**: 배송이 완료된 상태
-   **COMPLETE**: 주문이 최종 완료된 상태

### 이벤트 설명

-   **OrderAcceptEvent**: 주문 승인 이벤트
-   **OrderDeniedEvent**: 주문 거부 이벤트
-   **DeliveryStartEvent**: 배송 시작 이벤트
-   **DeliveryEndEvent**: 배송 완료 이벤트
-   **CompleteEvent**: 주문 완료 이벤트

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/example/springstatemachine/
│   │   ├── SpringStatemachineApplication.java     # 메인 애플리케이션 클래스
│   │   ├── config/
│   │   │   └── StateMachineConfig.java           # State Machine 설정
│   │   ├── controller/
│   │   │   └── OrdersController.java             # REST API 컨트롤러
│   │   ├── domain/
│   │   │   ├── Orders.java                       # 주문 엔티티
│   │   │   ├── OrdersEvent.java                  # 주문 이벤트 열거형
│   │   │   └── OrdersState.java                  # 주문 상태 열거형
│   │   ├── repository/
│   │   │   └── OrdersRepository.java             # 데이터 액세스 레이어
│   │   └── service/
│   │       ├── OrdersService.java                # 비즈니스 로직
│   │       └── OrdersStateChangeInterceptor.java # 상태 변경 인터셉터
│   └── resources/
│       └── application.yml                       # 애플리케이션 설정
└── test/
    └── java/com/example/springstatemachine/
        └── StateMachineConfigTest.java           # State Machine 테스트
```

## 실행 방법

### 1. 프로젝트 클론

```bash
git clone <repository-url>
cd spring-statemachine
```

### 2. JDK 설정

Java 25가 필요합니다. `JAVA_HOME`이 JDK 25를 가리키도록 설정하세요.

```bash
# Windows (PowerShell)
$env:JAVA_HOME = "C:\java\jdk-25.0.4.1"

# macOS / Linux
export JAVA_HOME=/path/to/jdk-25
```

### 3. 프로젝트 빌드

```bash
./mvnw clean compile
```

### 4. 애플리케이션 실행

```bash
./mvnw spring-boot:run
```

### 5. 테스트 실행

```bash
./mvnw test
```

> Windows에서는 `./mvnw` 대신 `mvnw.cmd`를 사용할 수 있습니다.

## API 사용법

애플리케이션이 실행되면 `OrderTest.http` 파일을 사용하여 API를 테스트할 수 있습니다.

### 주요 엔드포인트

| Method | URL                                | 설명                                     |
| ------ | ---------------------------------- | ---------------------------------------- |
| GET    | `/orders/get-orders`               | 주문 목록 조회                           |
| POST   | `/orders/new-orders`               | 새 주문 생성 (`{"name": "rice", "amount": 100}`) |
| POST   | `/orders/order-accept/{orderId}`   | 주문 승인 (`OrderAcceptEvent`)           |
| POST   | `/orders/order-denied/{orderId}`   | 주문 거절 (`OrderDeniedEvent`)           |
| POST   | `/orders/delivery-start/{orderId}` | 배송 시작 (`DeliveryStartEvent`)         |
| POST   | `/orders/delivery-end/{orderId}`   | 배송 완료 (`DeliveryEndEvent`)           |
| POST   | `/orders/complete/{orderId}`       | 주문 완료 (`CompleteEvent`)              |

> 현재 상태에서 허용되지 않는 이벤트를 보내면 상태 머신이 이벤트를 거부하며, 주문 상태는 변경되지 않습니다.

### H2 콘솔

-   URL: `http://localhost:8080/h2`
-   JDBC URL: `jdbc:h2:mem:szs`
-   User Name: `sa` (비밀번호 없음)

## 특징

-   **상태 머신 패턴**: 복잡한 상태 전환 로직을 명확하게 정의
-   **상태 변경 추적**: 모든 상태 변경 이벤트를 인터셉터로 추적
-   **데이터 지속성**: JPA를 통한 주문 데이터 저장
-   **RESTful API**: 표준적인 REST API 제공
-   **테스트 지원**: 포괄적인 단위 테스트 및 통합 테스트

## 개발 환경 설정

### 필수 요구사항

-   JDK 25
-   Maven 3.9 이상 (또는 포함된 Maven Wrapper `./mvnw` 사용)

### IDE 설정

-   IntelliJ IDEA 또는 Eclipse 사용 권장
-   Lombok 플러그인 설치 및 Annotation Processing 활성화 필요

## 업그레이드 내역 (Java 11 → 25)

-   Spring Boot 2.7.0 → 4.1.1, Spring State Machine 2.4.0 → 4.0.2
-   `javax.persistence` / `javax.transaction` → `jakarta.persistence` / Spring `@Transactional`
-   Spring Boot 4 모듈화에 맞춰 `spring-boot-starter-webmvc`, `spring-boot-h2console`, 모듈별 테스트 스타터 적용
-   Deprecated API 교체
    -   `JpaRepository.getOne()` → `findById().orElseThrow()`
    -   `StateMachine.start()/stop()/sendEvent(Message)` → `startReactively()/stopReactively()/sendEvent(Mono)`
    -   `resetStateMachine()` → `resetStateMachineReactively()`
-   Hibernate 7에서 제거된 `MySQL5InnoDBDialect` 설정 삭제 (H2 방언 자동 감지)
-   JDK 23+ 대응을 위해 Lombok을 `annotationProcessorPaths`에 명시
-   Maven Wrapper 3.3.4 (Maven 3.9.16)로 갱신
