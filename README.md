### Find_Mine

## 버전 관리

- ver 1.0.1
  - 회원 가입 추가 (SignUs.java)

- ver 1.0.3 
  - 단일 쓰레드에서 멀티 쓰레드로 바꿈
  - 게임 시간 추가(GameTime.java) 
  - sql 계정 만료로 sql 관련 코드는 잠시 무력화 시킴

- ver 1.0.4 
  - 점수제 도입

- ver 1.0.5
  - 처음 눌럿을때 지뢰 안나오게
  - 지뢰 눌렀을때 모드 지뢰 다나오게
  - 모든 swing component들 static으로 전환 
  - 블록 눌렀을때 액션 함수로 따로 만듦(EnableBlock)

- ver 1.0.6
  - 지뢰 개수 99개(설정 개수)로 안나오던 문제 해결
  - How To Play 작성
  - 메인 화면 완성
    - 빨간 깃발 갯수 칸 만들기
    - 중앙 상단에 이름 넣기
  - 첫 칸 눌렀을때부터 시간 흐르게 -> 그 전엔 로그인하면 바로 시간 흘렀음

- ver 1.0.7
  - 현재 게임 사용자의 정보를 담을 수 있는 클래스 추가(User.java) -> 이거 mySQL이랑 연결할 방법 찾아내자
  - 숫자로 표현된거 상수화
  - 게임 끝났을때 점수 갱신 추가  

- ver 1.0.8
  - mySQL에 연결 성공, 관리자용 계정 생성

- ver 1.0.9
  - Exception in thread "Thread-1" java.lang.Error: Interrupted attempt to acquire write lock 에러 해결
  - main 파일에 user 변수 활성화
  - 게임 끝났을때 최고 점수 갱신
  - Member class의 static 변수 member 의 초기화와 이의 리턴을 하나의 함수에서 하던걸 두개로 나눔
  - 메뉴바에 ranking 이랑 logout 추가
