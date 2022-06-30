### 정책은 docs 참고
[docs 참고](docs/POLICY.md)

### DB DDL
[resources/db 참고](src/main/resources/db)

### DB DIAGRAM
[diagram](src/main/resources/static/db_diagram.png)

### 실행하는법
h2 - mysql의 datasource, database-platform 만 주석 바꿔주면 됩니다.

mysql - 현재 application.yml에서 비밀번호와 schema 만 변경해주면 됩니다.

**API 실행하기**
[POST EVENT](http/event.http)
[포인트 조회](http/point.http)

**test 실행하기**
[test code 위치](src/test/java/com/triple/point)
```
./gradlew clean test
```
or
[test 실행](src/main/resources/static/gradle_test.png)


### todo list
- [X] db 설계
- [X] exception 처리 및 response 틀 잡기
- [X] POST /events
  - [X] history 남기기
  - [X] ADD, MOD, DELETE 로직 처리
- [X] 포인트 조회하기
- [X] 서비스 테스트 코드 작성 및 로직 검증
- [X] 컨트롤러 테스트 코드 작성
- [X] 메서드 이름 정리
- [X] 코드 정리