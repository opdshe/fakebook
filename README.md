# 👨🏻‍💻 페이스북 클론 프로젝트 (작성 중)

사용 기술 : 

Spring boot, JPA, MySQL, H2, Vue.js, Jenkins


## 코딩 컨벤션

- Lombok을 사용한다.
- 생성자의 인자 수가 4개 이상인 경우에 빌더패턴을 사용한다.
- 한 메서드의 길이는 10줄을 넘으면 안된다.
- tab 사용
- 테스트코드는 DisplayName 애노테이션을 사용하지 않는다.

```
    @Test
    public void 테스트_코드_이름() {
        ...
    }
```


## 프로젝트 관리

- Github의 Project 탭
    - TODO
    - IN PROGRESS
        - 동시에 1개만 진행한다.
    - DONE
        - Merge가 된 후에 Done으로 이동
        

## 브랜치 이름

- 마스터 브랜치 : master
- 개발 브랜치 : develop
- 기능 구현 브랜치 : feature/{issueNumber}/{issueName}
    - ex) feature/75/articlePaging
- **절대 master브랜치에 한 번에 업로드 하지 말 것!!**
- **브랜치 전략을 사용하여 깃 사용에 익숙해질 것**
    
    
## 업데이트 주기
- 매 주 일요일 develop -> master 업데이트


# Git commit 컨벤션
- Commit messsage 컨벤션 : [Commit message 참고](https://doublesprogramming.tistory.com/256)

- Pull Request Message : [#issueNumber] feat: issueName
    - ex) [#2] feat: 회원 등록, 조회, 수정, 삭제 기능
    
- Merge Commit Message : [Merge] [#issueNumber] feat: issueName
    - ex) [Merge] [#2] feat: 회원 등록, 조회, 수정, 삭제 기능
    
- develop -> master 업데이트 시 Commit : [VO.O.O]
    - ex) [V1.1.1]
    
    
## 배포 계획
1차 배포 : 회원가입, 로그인, 게시글 CRUD, 댓글 CURD, 프로필 (자신이 쓴 글 확인)  
2차 배포: 추후 결정


## 개발 기간
2021.01.12 ~ 


## 진행 상황
- [x] 로컬 데이터 베이스 연동
- [x] 회원 등록, 조회, 수정, 삭제 기능
    - 회원 등록 시 ID가 중복되면 예외 발생
    - 회원 정보 수정 시 변경 하려는 ID가 이미 존재하는 ID면 예외 발생
- [ ] 로그인 기능
- [x] 로그인 페이지 작성
- [ ] 피드 페이지 작성
- [ ] 게시글 등록, 조회, 수정,삭제 기능
- [ ] 댓글 등록, 조회, 수정,삭제 기능
- [ ] 프로필 페이지 작성
- [ ] 친구 추가 기능
- [ ] 공개 범위 설정 기능 (나만보기, 친구에게만 공개)
- [ ] 차단 기능
