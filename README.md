# TDD 웹 개발 삽질기 세미나 발표 코드
> 최초작성일: 16년 07월 23일

현재 화면은 계속적인 코드 정제작업 중입니다.
완료되면 해당 문구는 삭제 됩니다.

## Development Guidelines
> Required : node5 이상 / `npm install -g gulp`
> src : API 코드
> webapp : VIEW 코드

### Commit Rules
> 가능한 커밋마다 의미를 부여했고 각 부분에 특화 (api or view) 인 경우 scope 추가
> feat: 기능 추가
> refactor: 리팩토링
> test: 누락된 테스트코드
> chore: 잡다변경 ( 포멧팅, 설정변경 등)
```
# api 기능 추가
feat(api): 게시글 등록
~blank~
내용
~blank~
```


### 프로젝트 설정하기
```
git clone https://github.com/changhwa/tdd-example.git
cd tdd-example/webapp && npm install
```

### 프로젝트 실행하기(개발모드)
```
# API 서버
mvn spring-boot:run -Dspring.profiles.active=dev

# VIEW 서버
cd webapp && gulp
```

### 프로젝트 테스트코드실행 (현재는 API 서버만 지원합니다)
```
# UNIT TEST
mvn test

# ALL TEST
mvn test -P daily-build
```
