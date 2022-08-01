# Service URL
``` url
Swagger     : http://localhost:8080/swagger-ui/index.html
h2-console  : http://localhost:8080/h2-console/login.jsp
```

# 프로젝트 환경
```
Java Version : 11
Spring Boot : 2.7.2
Database : H2
gradle : 7.4.1
```

# gradle 추가 Library
```
h2              : com.h2database:h2
lombok          : org.projectlombok:lombok:1.18.24
log4j2          : org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16
jpa             : org.springframework.boot:spring-boot-starter-data-jpa
validation      : org.springframework.boot:spring-boot-starter-validation:2.7.0
spring security : org.springframework.boot:spring-boot-starter-security:2.6.8
jwt             : io.jsonwebtoken:jjwt-api:0.11.2 , io.jsonwebtoken:jjwt-impl:0.11.2 , io.jsonwebtoken:jjwt-jackson:0.11.2
mybatis         : org.mybatis:mybatis:3.5.10 , org.mybatis:mybatis-spring:2.0.7 , org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2
```

# 요구사항 구현여부, 구현방법, 검증결과
##### ※ JUnit Test : ApplicationTests.java 참고

##### 1. 회원 가입 [POST] - 구현여부 : 완
```json
Request Body

{
  "userName": "테스트",
  "userNickname": "test",
  "password": "A1q2w3e4r!@",
  "phoneNumber": "01012345678",
  "email": "test@naver.com",
  "gender": "M"
}
```

```
구현방법
1. 파라미터(이름, 별명, 비밀번호, 전화번호, 이메일, 성별)를 json 구조로 API 호출
2. 파라미터 제약 검사
3. 기존 등록되어 있는 이메일 조회
4. 이메일 미조회 시 : 회원 정보 등록, 임의 주문 정보 2개 등록(여러 회원 목록 조회에 사용)

※ 이미 등록되어 있는 이메일 조회 될 시 에러 표출
```

##### 2. 로그인 : JWT 토큰 발행 [POST] - 구현여부 : 완
```json
Request Body

{
  "email": "test@naver.com",
  "password": "A1q2w3e4r!@"
}
```


```
구현방법
1. 파라미터(이메일, 비밀번호)를 json 구조로 API 호출
2. Spring Security를 이용해 인증설정 후 JWT 생성

※ 회원 미조회 시 에러 표출
※ 회원 권한 없을 시 에러 표출
```
##### 성공 시
![image](https://user-images.githubusercontent.com/47163913/182144849-bca99503-8e7a-4b5a-b442-4dbc03ac7892.png)


##### 3. 단일 회원 상세 정보 조회 [GET] - 구현여부 : 완
```
Request Header

Authorization: Bearer [JWT]
```
![image](https://user-images.githubusercontent.com/47163913/182146207-4caba508-7b72-4869-8baa-44c2f2a59387.png)

```
실행방법
1. 로그인에서 생성한 JWT 데이터 복사
2. 상단 Authorize -> Value에 JWT 붙여넣기 -> Authorize 버튼 선택
3. 단일 회원 상세 정보 조회 API 호출
```

```
구현방법
1. Spring Security에 의해 JWT 조회 및 권한 체크
2. 회원 상세 정보 조회
```
##### 성공 시
![image](https://user-images.githubusercontent.com/47163913/182152440-343a4c02-a2dd-49c4-a673-851f22cd24c3.png)


##### 4. 단일 회원의 주문 목록 조회 [GET] - 구현여부 : 완
```
Request Header

Authorization: Bearer [JWT]
```

```
실행방법
1. 로그인에서 생성한 JWT 데이터 복사
2. 상단 Authorize -> Value에 JWT 붙여넣기 -> Authorize 버튼 선택
3. 단일 회원의 주문 목록 조회 API 호출
```

```
구현방법
1. Spring Security에 의해 JWT 조회 및 권한 체크
2. 단일 회원의 주문 목록 조회
```

##### 성공 시
![image](https://user-images.githubusercontent.com/47163913/182152844-17d5f59a-ee72-4622-8433-20dc45b9b55f.png)


##### 5. 여러 회원 목록 조회 [GET] - 구현여부 : 완
```
Request Header

Authorization: Bearer [JWT]
---

Request Parameters

userName=""
email=""
page=1
size=10
```

```
실행방법
1. 로그인에서 생성한 JWT 데이터 복사
2. 상단 Authorize -> Value에 JWT 붙여넣기 -> Authorize 버튼 선택
3. Request Parameters 값 입력
4. 여러 회원 목록 조회 API 호출
```

```
구현방법
1. Spring Security에 의해 JWT 조회 및 권한 체크
2. 여러 회원 목록 조회(각 회원의 마지막 주문 정보 조회)
```

##### 성공 시
![image](https://user-images.githubusercontent.com/47163913/182154232-8536b3a3-60b8-4ecb-bbce-e0a2c0ba3a73.png)

