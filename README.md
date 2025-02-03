# VIDCentral 📺

## 프로젝트 개요

VIDCentral는 사용자들이 비디오를 업로드하고 공유할 수 있는 플랫폼으로, 소통할 수 있는 공간을 제공합니다.

이 프로젝트는 비디오 플랫폼의 작동 방식을 이해하고 실습하는 것을 목표로 하며, 실질적인 구현을 통해 성능 개선을 테스트하고 최적화하는 데 중점을 두고 있습니다.

성능 개선을 통해 전체적인 프로젝트 성능을 향상시키고, 사용자에게 보다 빠르고 원활한 경험을 제공하는 것을 목표로 합니다.

## 개발 환경

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white" alt="Java"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot"> <img src="https://img.shields.io/badge/JPA-007396?style=for-the-badge&logo=java&logoColor=white" alt="JPA">
<br>

<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle"> <img src="https://img.shields.io/badge/intellij-000000?style=for-the-badge&logo=intellij-idea&logoColor=white" alt="IntelliJ IDEA"> <img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit 5"> <img src="https://img.shields.io/badge/h2-4CAF50?style=for-the-badge&logo=h2&logoColor=white" alt="H2">

## 구현 설명

1. **사용자 관리**
    - 사용자 등록, 로그인 기능, 사용자 정보 수정 기능을 제공하여 회원 정보를 관리합니다.
    - 보안을 위해 Spring Security와 JWT를 활용하여 사용자 인증 및 인가를 강화했습니다.
    - 프로필 이미지 업로드 및 수정 기능을 구현했습니다.

2. **비디오 관리**
    - 사용자가 비디오를 업로드하고, 필요에 따라 제목 및 설명을 수정하고 삭제할 수 있도록 구현했습니다.
    - 비디오 시청 시 시청 기록에 저장되어 나중에 확인할 수 있도록 구현했습니다.

3. **추천 알고리즘**
    - 사용자의 취향을 분석하여 비디오 영상을 추천하는 알고리즘을 구현했습니다.
    - 사용자의 시청 기록과 비디오 메타데이터를 기반으로 개인화된 비디오 조회 경험을 제공합니다.

4. **댓글 관리**
    - 사용자가 비디오에 댓글을 업로드, 수정, 삭제를 할 수 있는 기능을 구현했습니다.
    - 댓글 목록 조회 및 페이징 처리 기능을 추가하여 보다 나은 사용자 경험을 제공합니다.

5. **좋아요 및 싫어요**
    - 사용자가 비디오에 대한 의견을 표현할 수 있도록 좋아요와 싫어요 기능을 추가했습니다.
    - 이를 통해 사용자 참여를 유도하고, 비디오에 대한 피드백을 수집할 수 있습니다.

6. **API 문서화**
    - Swagger OpenAPI를 사용하여 API 문서화를 진행하였습니다.
    - 컨트롤러와 각 요청 및 응답 DTO에 대한 설명과 예시를 추가하여, API 사용자가 쉽게 이해할 수 있도록 했습니다.

## 요구 사항

- **회원 관리**
    - [X] 사용자 회원가입: 이메일 인증을 통한 사용자 등록
    - [X] 로그인: 이메일과 비밀번호를 통한 사용자 인증
    - [X] 비밀번호 재설정: 사용자 비밀번호 재설정 기능
    - [X] 닉네임 재설정: 사용자가 자신의 닉네임 변경 기능
    - [X] 사용자 프로필 사진 업로드: 프로필 사진 업로드 기능
    - [X] 사용자 프로필 조회: 자신의 프로필 정보를 조회할 수 있는 기능


- **비디오 관리**
    - [X] 비디오 업로드: 비디오 업로드 기능
    - [X] 비디오 조회: 특정 비디오 조회 기능
    - [X] 비디오 목록 조회: 비디오 목록을 페이징 처리하여 조회할 수 있는 기능
    - [X] 비디오 검색어 조회: 검색어를 통한 비디오 목록을 페이징 처리하여 조회할 수 있는 기능
    - [X] 비디오 수정: 비디오 제목 및 설명 수정 기능
    - [X] 비디오 삭제: 비디오 삭제 기능
    - [X] 비디오 조회수 표시: 각 비디오에 대한 조회수 표시 기능
    - [X] 비디오 추천 알고리즘: 사용자에게 추천 영상을 조회할 수 있는 기능


- **댓글 관리**
    - [X] 댓글 업로드: 비디오에 댓글 업로드 기능
    - [X] 댓글 목록 조회: 비디오에 댓글 목록을 페이징 처리하여 조회할 수 있는 기능
    - [X] 댓글 수정: 비디오에 댓글 수정 기능
    - [X] 댓글 삭제: 비디오에 댓글 삭제 기능


- **좋아요 및 싫어요 기능**
    - [X] 비디오에 좋아요 또는 싫어요 표시 기능
