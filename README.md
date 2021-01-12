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
    
    
## 업데이트 주기
- 1주 단위로 develop -> master 업데이트


## Commit 메세지

- [#{issueNumber}] {commit-type}: {commit-name}
    - ex) [#115] feat: 게시글 페이징 기능구현


## 배포 계획
1차 배포 : 회원가입, 로그인, 게시글 CRUD, 댓글 CURD, 프로필 (자신이 쓴 글 확인)
2차 배포: 추후 결정


## 개발 기간
2021.01.12 ~ 


## 진행 상황
- [ ] 회원 가입 기능
- [ ] DB연동
- [ ] 로그인 기능
- [ ] 로그인 페이지 작성
- [ ] 피드 페이지 작성
- [ ] 게시글 작성, 삭제, 조회 기능
- [ ] 댓글 작성, 삭제, 조회 기능
- [ ] 프로필 페이지 작성
- [ ] 친구 추가 기능
- [ ] 공개 범위 설정 기능 (나만보기, 친구에게만 공개)
- [ ] 차단 기능
