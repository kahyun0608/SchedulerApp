# ⚡️일정 관리 앱 서버⚡️
## 1. 기능 구현
- JDBC를 사용하여 일정을 등록, 조회(전체/선택), 수정, 삭제 할 수 있는 CRUD 기능
- 선택 일정을 수정, 삭제할 때 password를 받아 일치할 경우에만 기능 실행
  
## 2. API 명세서
https://documenter.getpostman.com/view/39354432/2sAY519Leg

## 3. ERD
<img width="283" alt="스크린샷 2024-10-31 오후 9 21 07" src="https://github.com/user-attachments/assets/2298923b-08b0-4952-8c00-e5842d16d7d7">

## 4. 어려웠던 문제
<details>
<summary>1. prepared statement 활용의 이해가 부족해서 placeholder `?`를 활용하는 문법에 오류 </summary>
<div markdown="1">
  <br>
```java
jdbcTemplate.query("select * from schedules where user_name = '?' order by updated_at desc", scheduleRowMapper(), userName);
```
처음에 `user_name`이 String 타입으로 들어가야하기때문에 동적인 `?`값을 따옴표로 감쌌는데 다음과 같은 에러가 발생하였다.

<img width="847" alt="2" src="https://github.com/user-attachments/assets/6577fc9e-40c3-41ec-8103-8a83fd949f2b">

하지만 이미 이후에 userName이라는 String 값을 넣어주기 때문에 ₩?₩에 따로 따옴표 처리를 해주지 않아도 되었다. 
```java
jdbcTemplate.query("select * from schedules where user_name = ? order by updated_at desc", scheduleRowMapper(), userName);
```
</div>
</details>

<details>
<summary> 상황에 따라 매개변수를 다양하게 받아 객체를 생성하는 문제 </summary>
<div markdown="1">
  <br>
그 때 그 때 다른 매개변수를 받아 객체를 생성하고 싶은데 생성자를 여러개 만들 수 있다는 사실을 잊고 다음과 같은 오류를 맞이하였다.  

<img width="1061" alt="스크린샷 2024-11-06 오후 4 54 40" src="https://github.com/user-attachments/assets/294e2d6d-16a9-4d4a-addb-5aec9078fbf3">  

Schedule 클래스에 클래스의 모든 필드를 매개변수로 생성하는 `@AllArgsConstructor` 이라는 Lombok Annotation을 사용하여 간편하게 새로운 생성자를 생성하였다.  
</div>
</details>

<details>
<summary> 수정 기능을 구현할 때 일부분만 수정하기 위해 예외처리를 하는 문제</summary>
<div markdown="1">
  <br>
  https://velog.io/@dg6080/241108-개인과제3-트러블슈팅
</div>
</details>

<details>
<summary> 칼럼이 될 변수 `contents`가 데이터베이스로 매핑할 때 `content`로 인식이 되는 오류</summary>
<div markdown="1">
  <br>
검색으로 content라는 변수가 있는지 오타가 났는지 검사하고 디버깅을 돌려보아도 요소가 모두 `contents` 로 적용되어야 하는데 실행을 돌리면 `content`로 인식이 되었다.

<img width="1165" alt="스크린샷 2024-11-08 오전 12 40 35" src="https://github.com/user-attachments/assets/79b5fdc4-44c0-47a6-bade-0bb82edf1869">

칼럼명도 오류에 맞추어 `content` 로 바꾸면 해결이 되는 것을 보아 매핑 과정에 오류가 생기는 것 같았고 schema를 여러번 삭제하고 재작성하는 과정에서 해결이 되어 정확한 원인을 파악하지 못하게 되었다. (처음 몇 번의 삭제/재작성 과정에서는 해결되지 않았다.)
</div>
</details>





## 5. 개선사항
- Repository 영역에 RowMapper를 두 개 생성하여 사용하고 있는데 (각각 responseDto와 entity List로 반환) 중복되는 요소가 많아 하나만 사용할 수도 있을 것 같다.
- Delete의 경우, 패스워드를 body로 받고 있는데 다른 방식으로 받는 것이 더 바람직해보인다.
- Layered Architecture 구조에 익숙하지 않아 기능 배분이 명확하지 않아 개선의 여지가 있다.
## 6. 소감
아직 많은 Annotation이나 Layered Architectuere 구조에 익숙하지 않고 또 데이터베이스와 매핑하는 부분이 많이 어려운 것 같다. 
또 중복되는 코드를 다듬거나 단순명료하게 논리를 짜는 연습이 필요할 것 같다.
하지만 이번 프로젝트를 진행하며 콘솔을 통해 오류를 찾고 해결하는 방법에 많이 익숙해졌으며 Postman 활용방법을 익힐 수 있어 좋았다.
