# Spring State Machine 주문 관리 시스템

## 프로젝트 개요

이 프로젝트는 Spring State Machine을 사용하여 주문 상태를 관리하는 웹 애플리케이션입니다. 주문의 생명주기를 상태 머신으로 모델링하여 체계적이고 안전한 상태 전환을 제공합니다.

## 기술 스택

-   **Java**: 11
-   **Spring Boot**: 2.7.0
-   **Spring State Machine**: 2.4.0
-   **Spring Data JPA**: 데이터 persistence
-   **H2 Database**: 인메모리 데이터베이스
-   **Lombok**: 코드 간소화
-   **Maven**: 빌드 도구

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

### 2. 프로젝트 빌드

```bash
./mvnw clean compile
```

### 3. 애플리케이션 실행

```bash
./mvnw spring-boot:run
```

### 4. 테스트 실행

```bash
./mvnw test
```

## API 사용법

애플리케이션이 실행되면 `OrderTest.http` 파일을 사용하여 API를 테스트할 수 있습니다.

### 주요 엔드포인트

-   `POST /orders` - 새 주문 생성
-   `GET /orders/{id}` - 주문 조회
-   `PUT /orders/{id}/approve` - 주문 승인
-   `PUT /orders/{id}/deny` - 주문 거부
-   `PUT /orders/{id}/start-delivery` - 배송 시작
-   `PUT /orders/{id}/end-delivery` - 배송 완료
-   `PUT /orders/{id}/complete` - 주문 완료

## 특징

-   **상태 머신 패턴**: 복잡한 상태 전환 로직을 명확하게 정의
-   **상태 변경 추적**: 모든 상태 변경 이벤트를 인터셉터로 추적
-   **데이터 지속성**: JPA를 통한 주문 데이터 저장
-   **RESTful API**: 표준적인 REST API 제공
-   **테스트 지원**: 포괄적인 단위 테스트 및 통합 테스트

## 개발 환경 설정

### 필수 요구사항

-   Java 11 이상
-   Maven 3.6 이상

### IDE 설정

-   IntelliJ IDEA 또는 Eclipse 사용 권장
-   Lombok 플러그인 설치 필요

## 기여하기

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.
