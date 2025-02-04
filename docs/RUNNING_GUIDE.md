## VIDCentral 애플리케이션 실행 방법

이 애플리케이션을 실행하기 위해서는 다음의 준비가 필요합니다.

### 1. H2 데이터베이스 설정
- H2 데이터베이스가 실행되어야 합니다.
- 기본 정보:
    - **URL**: `jdbc:h2:mem:testdb`
    - **아이디**: `sa`
    - **비밀번호**: (비밀번호 없음)
- H2 콘솔을 사용하려면 아래 URL로 접속합니다:
    - **H2 콘솔 URL**: `http://localhost:8080/h2-console`

### 2. Redis 설정 (MAC)
- Redis가 실행 중인지 확인하는 명령어:
  ```bash
  redis-cli ping
  ```

- Redis를 실행하는 명령어:
  ```bash
  brew services start redis
  ```
  ```bash
  redis-server
  ```

- Redis가 정상적으로 연동되었는지 확인하는 명령어:
  ```bash
  redis-cli ping
  ```

- Redis가 실행 중인지 확인하는 명령어:
  ```bash
  redis-cli ping
  ```

### 3. 추가적인 내용
- 필요에 따라 애플리케이션 실행 시 필요한 다른 환경 설정이나 의존성에 대한 정보도 추가하도록 하겠습니다.
