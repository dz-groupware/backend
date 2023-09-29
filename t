[33mcommit 9fc879c6edcf620f35771ab8090f62844e5377e6[m[33m ([m[1;36mHEAD -> [m[1;32mkimaenzu[m[33m, [m[1;31morigin/kimaenzu[m[33m)[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Tue Sep 26 23:14:09 2023 +0900

    [feat]: 라우팅 리스트 반환 api 추가
    
    로그인 응답에 empId, compId를 포함하도록 수정
    
    라우팅 리스트 반환하는 요청 추가
    [menu]
    getMenuList

[33mcommit 10520ab787c2febda9f64b2c68ba1b24af7eb01a[m
Merge: 7f0bc6f d6a4ee2
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Tue Sep 26 20:23:30 2023 +0900

    Merge branch 'dev' of github.com:dz-groupware/backend into kimaenzu

[33mcommit 7f0bc6f5f8ed394b3e141a78c517579c78cf9ef2[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Tue Sep 26 20:03:37 2023 +0900

    [refactor]: 로그인시 empId, compId 넘겨주도록 변경

[33mcommit d6a4ee20d52e43f358ed5619f25be5844eaaf93a[m[33m ([m[1;31morigin/dev[m[33m)[m
Merge: fe7c58f 718688e
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Tue Sep 26 20:01:37 2023 +0900

    Merge pull request #25 from dz-groupware/subo
    
    [refactor]: JWT 코드 리팩토링

[33mcommit 718688e93747d8a9c680c3be1fe447e3a11d8900[m
Merge: aa0da91 fe7c58f
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 26 19:55:20 2023 +0900

    Merge branch 'dev' of https://github.com/dz-groupware/backend into subo

[33mcommit fe7c58f4e813efd2d6ae69e5dda9acb1f1f4bc93[m
Merge: d6c4e62 e7eb9b2
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Tue Sep 26 19:53:34 2023 +0900

    Merge pull request #24 from dz-groupware/kimaenzu
    
    empId 형변환 에러 수정

[33mcommit aa0da912ca2da4fbdfaa3b68b2a012eee18d520b[m
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 26 19:09:34 2023 +0900

    [feat]: 커스텀 에러 생성
    <body>
    BuisnessLogicException.java 생성
    JwtExceptionCode.java 생성
    RestControllerAdvice 애노테이션 미적용

[33mcommit f94bd6527807ddd2a8797bc5dd5d2645eb72898e[m
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 26 17:47:43 2023 +0900

    [refactor]: 토큰해독 로직 코드 리팩토링

[33mcommit 972c6210eb158ff12375239502ab308be60dfda2[m
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 26 17:42:08 2023 +0900

    [refactor]: 리프레쉬 토큰 생성부분 코드 리팩토링

[33mcommit 6b24737007e8111312fad1a39896ba803c7ae167[m
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 26 17:33:18 2023 +0900

    [refactor]: jwt생성 로직 코드 리팩토링

[33mcommit 2e8d180e7a6fa593b0f5180067a0177dbcc268fb[m
Merge: 31bae51 ae9d8ff
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 26 15:34:55 2023 +0900

    Merge branch 'subo' of https://github.com/dz-groupware/backend into subo

[33mcommit 31bae516c0b1eb108292d1ad1c76da5e25abb541[m
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 26 15:33:54 2023 +0900

    [test]: 배포테스트용 api생성

[33mcommit ae9d8ff8cefc810095a613ce1f15521e0b5da627[m
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Tue Sep 26 15:23:38 2023 +0900

    Update application.yml

[33mcommit 7ded61e59fc3c03eb10598495ebcc9cdee94a9c0[m
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Tue Sep 26 14:00:47 2023 +0900

    Update application.yml

[33mcommit 2b9c316830f3c08b08c9837297a187d30e6610c6[m
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Tue Sep 26 13:41:42 2023 +0900

    Update application.yml

[33mcommit 8bfdcb0fea1fc8081ea4f771ee71058f011a67b9[m
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Tue Sep 26 11:02:11 2023 +0900

    [chore] : application.yml 추가
    
    프로덕션용 설정파일

[33mcommit adf40ae8b1c5cb6538b1755145c830ed7f5e20a4[m
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 25 21:28:23 2023 +0900

    [chore]: start_server.sh 재수정

[33mcommit 21370d0fd44b0f5d6cbac7299de403a514be36b9[m
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 25 21:22:18 2023 +0900

    [chore]: start_server.sh 수정

[33mcommit fa856e36a9a9ab8797106d9206c2d774449c8d02[m
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 25 21:14:33 2023 +0900

    [chore]: start_server.sh 경로변경

[33mcommit f394fd58a1a63f488fa12b9980c80b81688fa179[m
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 25 21:00:43 2023 +0900

    [feat]: start_server.sh 추가

[33mcommit e7eb9b29b8543a39e729f328d2d357ace1d630b1[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Mon Sep 25 19:43:05 2023 +0900

    [fix]: 형변환 에러 수정

[33mcommit cba05bb57421290690adf0c90b9489e6d0c3f8fb[m
Merge: 995f245 d6c4e62
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Mon Sep 25 19:26:10 2023 +0900

    Merge branch 'dev' of github.com:dz-groupware/backend into kimaenzu

[33mcommit 995f245bb1580eb90d963989e8ad53cbc4cf6063[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Mon Sep 25 19:02:16 2023 +0900

    For Merge

[33mcommit d6c4e62ef891640db848fd6bba059e89eb6fecff[m
Merge: 7b09955 101e21f
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Mon Sep 25 19:01:32 2023 +0900

    Merge pull request #23 from dz-groupware/subo
    
    [feat]: 권한 매핑 Api, Jwt 토큰 구현

[33mcommit 101e21f859bcff64547e9e2d61ac5c8fb681802d[m
Merge: 59c07ac 7b09955
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 25 19:00:34 2023 +0900

    Merge branch 'dev' of https://github.com/dz-groupware/backend into subo

[33mcommit 7b0995597d5257b12f217c0e97a5661b6fc1739a[m
Merge: 9429e7d 3e2c1f7
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Mon Sep 25 14:15:15 2023 +0900

    Merge pull request #22 from dz-groupware/kimaenzu
    
    refactor

[33mcommit 9429e7d737472f0208c183adb6d4708e8b833f79[m
Merge: 2dbcafa f504626
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Mon Sep 25 14:14:59 2023 +0900

    Merge pull request #21 from dz-groupware/jane
    
    [feat]: 사원관리페이지 수정 및 버그 수정

[33mcommit f5046261d6103f9135dac7b51ce20ed75b051ab1[m
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Mon Sep 25 13:21:48 2023 +0900

    [feat]: 사원관리페이지 수정 및 삭제기능 추가
    
    <body>
    
    <footer>

[33mcommit 59c07ac70eb4aef1296e84f0b7a567d026a0e8da[m
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 25 09:24:10 2023 +0900

    [refactor]: jwt토큰 코드 수정

[33mcommit 5754a3a029753db3e0da8a953d24b9fd25dd74cf[m
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 25 01:37:50 2023 +0900

    [feat]: 직원, 권한 매핑 api 완성

[33mcommit 3e2c1f7d9bdda92ca679cb8348aad9081a1344b8[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Sun Sep 24 17:01:08 2023 +0900

    [refactor]: user 도메인 파라미터 수정
    
    [user]
    getAnotherLogin 수정
    PkDto를 사용하여 사용자 정보를 가져오는 방식으로 수정

[33mcommit 578c71ec6c3ff061d704e6b369d02661d2adeaab[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Sun Sep 24 16:41:39 2023 +0900

    [refactor]: redis에 사용자 정보저장/사용 (filter)
    
    [AuthorizationMenuFilter 수정]
    : 가장 마지막에 실행되는 필터. controller에 사용자 정보 전달
    - 토큰이 유효한지 검사
    - 토큰에서 empId만 사용
    - empId를 key로 NoSQL에서 사용자 정보 조회 (서비스에서 이용)
    (없을 경우 SQL에서 조회 후 NoSQL에 저장)
    - jwt를 key로 이용가능한 메뉴 리스트 조회
    (없을 경우 SQL에서 조회 후 NoSQL에 저장)
    - 접근한 메뉴가 메뉴리스트에 없으면 403 응답 반환
    
    PkDto 추가
    : 사용자 정보 저장용
    
    RedisAspect.java 삭제
    : 사용자 정보를 위한 AOP -> 필터로 기능 옮김
    
    RedisMapper
    - 메뉴 리스트 찾는 mapper
    - empId로 PkDto 필드 정보를 찾는 mapper
    
    RedisService
    - testRedis : 현재 토큰으로 가져올 수 있는
    사용자 정보 확인용
    - flushDB : db 데이터 전체 삭제용

[33mcommit 5fe769876e9899d382edabdcb804cae5be2fbc02[m
Merge: c710c2a 7930395
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Sun Sep 24 00:19:59 2023 +0900

    Merge branch '0924' into kimaenzu

[33mcommit 79303951ea8f31dcedb2d22c982a0a14d9e3d9c3[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Sat Sep 23 23:49:30 2023 +0900

    [feat]: 레디스 관련 기능 추가 및 관련 도메인 수정
    
    [ redis 관련 기능 ]
    
    - AuthorizationMenuFilter.java
        - 사용자가 메뉴(페이지)를 이용가능하는지 확인하는 용도
        - key : jwt / value : menu Set
        - DB 1
        - `redisForMenu`
    - RedisAop.java
        - 사용자 정보 조회 용도
        - key : jwt / value : JwtDto
        - DB 2
        - `redisForPayload`
    
    -  토큰 확인
    : 서비스에서 읽은 사용자 정보 (토큰 payload)
    -  DB 데터 전체 삭제
    : flushDB
    ---
    
    payload 가져오는 방식 수정
    
    - department, menu, modal, setting
    
    (수정 방법 노션 참고)
    ---
    
    페이지 이동 문제 (수정중)
    
    menuId가 없는 페이지의 경우 ex)Home(main)
    
    권한이 없어 페이지 접근 불가 ⇒ home에서 403error ⇒ 로그인 페이지로 다시 이동
    
    메뉴 관련 데이터 수정 필요

[33mcommit d537c974cffb75bab8b60e186b1f68a598a6d5e5[m
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Fri Sep 22 14:01:12 2023 +0900

    [feat]: 사원관리페이지 수정 및 버그 수정
    
    <body>
    
    <footer>

[33mcommit e618965668c2fb15fed0860099e1fb5515586e25[m
Author: subo <kws3363@gmail.com>
Date:   Fri Sep 22 00:47:24 2023 +0900

    [feat]: 유저트리 Api 작성

[33mcommit 73884eb05e351ea2f0411007ab3d69ab5a5f2fcf[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Sep 21 22:38:38 2023 +0900

    [refactor]: 이용가능한 페이지 확인 필터
    
    Authorization : 이용 가능한 페이지인지
    jwt 토큰의 정보를 통해 확인

[33mcommit c710c2a88126fe8b1b69194b6fbc2d31838cb8f2[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Sep 21 22:21:42 2023 +0900

    [feat] : 필터 분리 및 저장 시도
    
    - redis 와 관련된 설정과 코드 제거
    - JwtGetFillter 에선 jwt 토큰을 가져오는 역할을
    - AuthorizationMenuFilter에선 메뉴 리스트를 조회해 접근 권한이 있는지
      확인하려고 하였음
    - filter 에서 추출한 정보를 서비스로 주기가 어려움
    jwt 토큰의 값을 가져오는 방법을 다시 생각해 봐야 할 거 같음

[33mcommit 027ac7025a4a72de35b78dfaee5aa007416a0fd6[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Wed Sep 20 23:18:46 2023 +0900

    [refactor]: 일부 필드 저장 안되는 부분 수정
    
    [redis]
    - SecurityUtil 을 통해 토큰에서 값 받아오던 방식
    RedisService를 통해 토큰과 토큰의 값을 받아오도록 변경
    - jwt 토큰 받아오도록 추가
    department, menu, setting
    
    [department]
    - 부서 저장 수정 : 일부 필드 저장 안되는 부분 수정
    
    [setting]
    - 메뉴 수정 : 일부 필드 저장 안되는 부분 수정
    
    [menu]
    - gnb 가져오는 쿼리 수정

[33mcommit 3a5f2c0cdc4b9aeea418be148505db8a08c778fc[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Tue Sep 19 23:05:02 2023 +0900

    [feat]: redis에 jwt토큰과 메뉴 리스트 저장
    
    redis에 <k,v>  jwt 토큰 List<String>
    형식으로 저장하도록

[33mcommit c1cacf43f0dc126f5a8b5e77808d82ccbda83b8b[m
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 19 17:50:40 2023 +0900

    [refactor]: 만료시에 로그인 url 안가지는 버그 수정.

[33mcommit d90f496227b7995d7a918eb45dda78dbfd7a6fd4[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Mon Sep 18 23:10:02 2023 +0900

    [feat] : 부서 페이지 저장/수정/삭제/일괄등록 API
    
    - 저장 버튼 클릭시 리스트를 누른 정보면 수정,
    추가 버튼을 누른 정보면 저장
    - 삭제 버튼 클릭시, 데이터 삭제 (deleted_yn = 1)
    하위 모든 부서도 같이 수정
    
    (수정 중)
    - 일괄등록 : 추가/수정 등 수정 정보를 한번에 저장할 수 있는 기능
    추가의 경우 리스트에 뜨지 않기 때문에
    수정된 정보를 한번에 볼 수 있는 모달을 먼저 띄우고,
    삭제/저장 버튼을 클릭하게 할 예정
    
    (수정 중)
    부서 상위부서 변경시 id_tree, name_tree 변경

[33mcommit da74eed4ca05c11ca93bce13a0e4933020642d1f[m
Merge: 4d8d3b9 2dbcafa
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 18 14:26:17 2023 +0900

    Merge branch 'dev' of https://github.com/dz-groupware/backend into subo

[33mcommit 4d8d3b9faa79ad040f54c911c1d9d1046d1e6167[m
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 18 14:24:09 2023 +0900

    [refactor]: 로그인 이슈 해결

[33mcommit 27067eeaa1e7a1541c0c966711fda985a0749723[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Sat Sep 16 15:47:17 2023 +0900

    [feat]: 부서관리 페이지 부서 리스트/상세 API 추가
    
    - 부서 가장 상위 메뉴 조회
    - 선택 부서 하위 메뉴 조회

[33mcommit 70b823c66529bacc36ddacb19d56157eeb0c6342[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Fri Sep 15 22:30:10 2023 +0900

    [feat]: 부서 도메인 추가
    
    - 부서 기본 리스트 가져오기
    - 부서 하위 부서 가져오기
    - 부서 정보 저장

[33mcommit 5e51cd41e134cb2ef1d4d2a8bbe37ffebeadd38f[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Fri Sep 15 13:40:16 2023 +0900

    [refactor]: 메뉴 설정 페이지 메뉴 검색 및 수정 기능
    
    - 상위 메뉴 선택 팝업 : 관리자/마스터 쿼리 구분. 현재는
    모두 관리자 버전으로 상위 메뉴 조회 가능
    
    - 검색 메뉴 상세 : 상위 메뉴 필드에 선택한 메뉴의 상위메뉴를
    띄울 수 있도록 반환 dto, 쿼리 수정(sortOrder)
    
    - 메뉴 수정 기능 : 상위메뉴만 수정되는 문제 해결

[33mcommit c55a961980bc200785f9efefbd1f37e96063d743[m
Merge: 155d667 2dbcafa
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Fri Sep 15 09:54:59 2023 +0900

    Merge branch 'dev' of github.com:dz-groupware/backend into kimaenzu

[33mcommit 155d667c5af8e960a21cf52d30b359151aa06b2e[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Fri Sep 15 09:54:41 2023 +0900

    for merge

[33mcommit 2dbcafa5bf7dd3222cfe3cda8c3c5f2b8da9d488[m
Merge: 27932d5 bfd8dc1
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Fri Sep 15 09:39:14 2023 +0900

    Merge pull request #19 from dz-groupware/jane
    
    [feat] : 사원관리페이지 리스트 추가 및 회사관리페이지 수정

[33mcommit bfd8dc1d07b0e7ce4bb0e690cb9ac3c5a0918b5d[m
Merge: da41048 27932d5
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Fri Sep 15 09:38:58 2023 +0900

    Merge branch 'dev' into jane

[33mcommit da41048661e351000702f87f2bb79dcdf57057fe[m
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Fri Sep 15 09:24:34 2023 +0900

    [feat]: 사원관리페이지 추가 및 회사관리페이지 트리입력수정
    
    <body>
    
    <footer>

[33mcommit 6cf99c6dc2173d1d27669df6d52251d7dee3d652[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Sep 14 23:39:06 2023 +0900

    [refactor]: 조직도 팝업 검색 에러 수정
    
    [modal]
    - 조직도 검색 결과, 회사/부서 선택시 부서 리스트 반환 쿼리 수정
    - TreeItemRes DTO 수정

[33mcommit 7363e8eb6c942e5a20a02fe599c4e8036c1a0304[m
Merge: 1656e2b 27932d5
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Sep 14 20:28:10 2023 +0900

    Merge branch 'dev' of github.com:dz-groupware/backend into kimaenzu

[33mcommit 1656e2bcb98e0ba1eb5433867153490907e53d46[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Sep 14 20:26:20 2023 +0900

    [refactor]: 메뉴 수정 로직
    
    - IT 메뉴를 빅데이터 메뉴로 이동시킬 때
    - 데/빅 메뉴를 데이터 분석 메뉴로 이동 시킬 때
    - 시스탬 개발 메뉴를 정보보안 메뉴로 이동시킬 때
    
    간단 테스트 진행
    
    기능은 사용 가능하지만
    추가 진행 및 중복 코드 분리 필요.

[33mcommit 27932d571a6ffb777519d2a65f901ced6e32660b[m
Merge: 258d3d3 8ac5fb8
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Thu Sep 14 19:40:12 2023 +0900

    Merge pull request #18 from dz-groupware/subo
    
    [feat]: 권한 생성 Api 작성

[33mcommit 8ac5fb8379a9c4e469879671860c443327f3a889[m
Merge: 4409599 258d3d3
Author: subo <kws3363@gmail.com>
Date:   Thu Sep 14 19:39:04 2023 +0900

    Merge branch 'dev' of https://github.com/dz-groupware/backend into subo

[33mcommit 44095999569d7640d33267c81387ab1b44e7c928[m
Author: subo <kws3363@gmail.com>
Date:   Thu Sep 14 19:37:56 2023 +0900

    [feat]: 권한 생성 Api 작성

[33mcommit 258d3d371e46ecd51a1aa1b68ae273099600a558[m
Merge: c3751d1 99d47df
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Thu Sep 14 19:37:32 2023 +0900

    Merge pull request #17 from dz-groupware/kimaenzu
    
    [refactor] : 프로필 쿼리 수정 및 메뉴 수정 로직(진행중)

[33mcommit 99d47df249d2598ad1f094b0dd33cc8774f28b39[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Sep 14 19:21:04 2023 +0900

    [refactor] : 프로필 가져오는 쿼리 및 메뉴 수정 로직
    
    - 프로필 : 사원/마스터의 경우 모두 데이터를 반환할 수 있는
    쿼리로 수정
    
    - 메뉴 수정 로직(수정 중) : 상위에서 하위로 메뉴를 이동하는 경우
    자신의 하위 메뉴로 이동하는 경우 (미완)

[33mcommit 0961c694a5ee6f631f53aa7c4c4e9dac3e9b4834[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Wed Sep 13 18:39:38 2023 +0900

    error

[33mcommit d2a271ccd76766d781523af5850c412a0084e13d[m
Merge: b804c84 c3751d1
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Wed Sep 13 17:23:30 2023 +0900

    Merge branch 'dev' of https://github.com/dz-groupware/backend into jane

[33mcommit b804c84366739ff4fded8e01b2c3b542e292e904[m
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Wed Sep 13 17:19:33 2023 +0900

    [fix]: 회사관리페이지 idtree 및 nametree로직 추가
    
    <body>
    
    <footer>

[33mcommit 1f538dcd9ba9e344e7d040c94d08dec638805667[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Tue Sep 12 17:07:40 2023 +0900

    [refactor]: 메뉴 설정 페이지 추가/수정/삭제
    
    - 메뉴 추가/수정 기능 수정
    - 메뉴 삭제 기능 추가 (미사용)

[33mcommit d7cb63261d550c74dfc400bc2fca2f8db7749649[m
Merge: b5068f9 c3751d1
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Mon Sep 11 15:53:33 2023 +0900

    Merge branch 'dev' of github.com:dz-groupware/backend into kimaenzu

[33mcommit b5068f99d8ed9c2d844f23ce778b53a6f925c976[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Mon Sep 11 15:52:59 2023 +0900

    commit for Merge

[33mcommit c3751d135b460edeffc59a6910c1861fb6ab0343[m
Merge: f32a5d1 218a846
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Mon Sep 11 15:51:16 2023 +0900

    Merge pull request #16 from dz-groupware/subo
    
    [feat] : 재로그인 Api 추가

[33mcommit 218a8463fd1ed177009a88f3d723288db687c921[m
Merge: 6db91d4 f32a5d1
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Mon Sep 11 15:51:08 2023 +0900

    Merge branch 'dev' into subo

[33mcommit f32a5d15d490577f8ef1b4cd1af10b376ed7cf8c[m
Merge: f302bc8 c7de4f6
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Mon Sep 11 15:49:39 2023 +0900

    Merge pull request #15 from dz-groupware/kimaenzu
    
    [feat]: 도메인 추가 및 수정( gnb, menu, modal )

[33mcommit c7de4f61bf9c56831ee02b67f95bd4ad22906491[m
Merge: f3ef0a6 f302bc8
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Mon Sep 11 15:49:30 2023 +0900

    Merge branch 'dev' into kimaenzu

[33mcommit 6db91d491f50dab44fb6d9c69c77e86bb687d95a[m
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 11 15:45:20 2023 +0900

    [feat]: 로그인시 계정 선택 api추가
    url: /api/v1/user/re-login
    method: post
    requestBody: empId
    cookie: JWT

[33mcommit f3ef0a6e04eb7ed6d2653e80a8d20e198241a007[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Sun Sep 10 22:32:41 2023 +0900

    [feat] : 이미지 리스트 가져오기 및 기타 쿼리 수정
    
    [user]
    => userMapper.xml에 limit1 빠져 있어서 추가 했습니다.
    
    [menu]
    '/lnb' 쿼리 수정
    
    [modal]
    - 모든 프로필 정보 가져오기(변경) : 마스터와 일반 쿼리문 구분 -> 같은 쿼리 사용
    - 조직도 - 사원 리스트 : 쿼리 수정
    - 조직도 검색 - 사원 (전체) : 쿼리 수정
    
    [메뉴설정 - 이미지]
     - S3 이미지 리스트 가져오기
     - 이미지 저장

[33mcommit 5b3b8cafaaec6545f58fe999ccc3148b4e0b29b4[m
Author: subo <kws3363@gmail.com>
Date:   Fri Sep 8 21:30:54 2023 +0900

    [chore]: 권한 매퍼쪽 변경중 임시저장

[33mcommit 4da738f39be42d8c023a5aeb7e530bc9ed900f9b[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Fri Sep 8 17:58:51 2023 +0900

    [feat]: 도메인 추가 및 수정( gnb, menu, modal )
    
    - gnb 도메인 : 한 요청에 여러 service를 사용하는 도메인을 위해 만듦
    
    - gnb/favor 리스트 가져오는 매퍼 수정
    - 프로필 가져오는 매퍼 수정
    - 마스터/사원존재 확인 (checkMapper) 추가
    - 조직도 트리 매퍼 수정
    - 사원 리스트 수정
    - 조직도 검색 매퍼 중 전체 옵션 매퍼 수정

[33mcommit f302bc8fb00f3c66400416c049ec5505f6660d88[m
Merge: 42aff49 883da1f
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Thu Sep 7 20:34:28 2023 +0900

    Merge pull request #13 from dz-groupware/subo
    
    [fix]: 로그인 버그 수정

[33mcommit 883da1f3ce69ec6ce53960ab3238be49a025b8a1[m
Author: subo <kws3363@gmail.com>
Date:   Thu Sep 7 20:32:59 2023 +0900

    [fix]: 로그인 버그 수정

[33mcommit 4f4bcef8750ddf3f4ded4aeeedfce973e7a3d956[m
Merge: c1c25b0 42aff49
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Sep 7 14:30:28 2023 +0900

    Merge branch 'dev' of github.com:dz-groupware/backend into kimaenzu

[33mcommit c1c25b05544758087888cc68e7aed62ad9e04bb1[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Sep 7 14:30:13 2023 +0900

    ''

[33mcommit 42aff494e5ebc61fc336bb3de6a033f43e3ac199[m
Merge: a87e416 91bafdd
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Thu Sep 7 12:17:59 2023 +0900

    Merge pull request #12 from dz-groupware/subo
    
    [fix]: 쿼리 수정

[33mcommit 91bafdd7c9abc73ab4c081627a9ef396a6491f45[m
Merge: b05a767 a87e416
Author: subo <kws3363@gmail.com>
Date:   Thu Sep 7 10:12:33 2023 +0900

    Merge branch 'dev' of https://github.com/dz-groupware/backend into subo

[33mcommit b05a767e57ba91fc4e46497b5f16d76de389b901[m
Merge: 545cb3a 5131e27
Author: subo <kws3363@gmail.com>
Date:   Thu Sep 7 10:10:14 2023 +0900

    Merge branch 'dev' of https://github.com/dz-groupware/backend into subo
    [fix]: merge 충돌 수정

[33mcommit a87e416c21af1dc338e3833f1f80d7d3b6a1a0e2[m
Merge: 5131e27 9459265
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Thu Sep 7 10:09:15 2023 +0900

    Merge pull request #11 from dz-groupware/jane
    
    [fix] : 회사관리페이지 수정

[33mcommit 9459265543ccd29d5b0e808445f562cedc5caf1f[m
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Thu Sep 7 10:04:03 2023 +0900

    [fix]: 회사관리페이지 수정

[33mcommit 5131e2722cff2bb3dccaf752f2a3ada46be3dfa0[m
Merge: b82f678 e3ffa65
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Thu Sep 7 10:00:18 2023 +0900

    Merge pull request #10 from dz-groupware/kimaenzu
    
    [fix] : main, modal 도메인 일부 수정

[33mcommit 545cb3a6eecb863c7f9aad07bd11ec7a6489100f[m
Author: subo <kws3363@gmail.com>
Date:   Thu Sep 7 09:58:40 2023 +0900

    [fix]: 로그인 상태코드 안보내지는 버그 수정

[33mcommit e3ffa6569d82f473abcc5b988940128fe29c5d39[m
Merge: 31a071e b82f678
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Sep 7 09:01:00 2023 +0900

    [fix] : main, modal 도메인 일부 수정
    
    - 로그인 후 토큰 확인 완료
    
    [menu 도메인] (수정중)
    
    - 사원/부서/회사 존재여부를 확인하는 mapper 추가 (check)
    
    - GNB 메뉴 리스트 mapper 수정
    
    - 응답 형식 통일 (ResponseEntity, SingleResponseDto)
    
    [modal 도메인] (수정중)
    
    - 모든 프로필 정보를 가져오는 mapper 수정
    
    - 조직도 트리 mapper 수정
    
    - 사원 리스트 mapper 수정

[33mcommit 31a071e9e7ea2739c7c76e64f55126651da9af9c[m
Merge: 9ac05fd 1fad4ca
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Wed Sep 6 15:37:07 2023 +0900

    Merge branch 'dev' of github.com:dz-groupware/backend into kimaenzu

[33mcommit 45d2b2707d75f4b730a7aa4131a20d1088c11ca0[m
Merge: fd17073 b82f678
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Wed Sep 6 15:36:06 2023 +0900

    Merge branch 'dev' of https://github.com/dz-groupware/backend into jane

[33mcommit b82f678b648758a1e1f174fb909da06bf351d7c7[m
Merge: f3d674e 7ef417e
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Wed Sep 6 15:32:01 2023 +0900

    Merge pull request #9 from dz-groupware/subo
    
    [fix]: jwt토큰 쿠키에 안담기는 버그 수정

[33mcommit fd17073df1b82479672d5c2708f3698d742a9416[m
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Wed Sep 6 15:30:16 2023 +0900

    [feat]: 사원페이지 백엔드수정

[33mcommit 7ef417eee352b1b776ac93d34f761abd4b0e600b[m
Author: subo <kws3363@gmail.com>
Date:   Wed Sep 6 15:29:20 2023 +0900

    [fix]: jwt토큰 쿠키에 안담기는 버그 수정

[33mcommit f3d674e97937815c43ac842677b8be07ffbac951[m
Merge: 1fad4ca 69bff26
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Wed Sep 6 13:22:40 2023 +0900

    Merge pull request #8 from dz-groupware/subo
    
    [refactor] jwt토큰 헤더에서 쿠키로 받을 수 있도록 수정

[33mcommit 69bff26738a6f839d5187b10a51de5a5330d56fc[m
Author: subo <kws3363@gmail.com>
Date:   Wed Sep 6 13:19:52 2023 +0900

    [refactor]: jwt토큰 헤더에서 쿠키로 수정

[33mcommit e2b1e0428c244d58f3087a752688d7ed2e5660f0[m
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 5 12:14:26 2023 +0900

    [fix]: application.yml 올라가는 문제 해결

[33mcommit 5f6a0cccf10c7cca6d2377eafe1fbd4d0b71b676[m
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 5 12:11:40 2023 +0900

    [docs]: .gitignore 수정

[33mcommit d9b0325e63b5491da0535e5b1136b02037eade0c[m
Author: subo <kws3363@gmail.com>
Date:   Tue Sep 5 12:00:08 2023 +0900

    [fix]: 함수명명법 수정, git 추가시 yml 제외

[33mcommit e91f1308717771e2cad0c7ebd3798c32413273d8[m
Merge: b42a73a 1fad4ca
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Tue Sep 5 11:43:45 2023 +0900

    Merge branch 'dev' of https://github.com/dz-groupware/backend into jane

[33mcommit b42a73a6d898f122fbf04caca802b32672cc9a43[m
Merge: 37ac0a4 b633e0d
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Tue Sep 5 11:43:11 2023 +0900

    Merge branch 'jane' of https://github.com/dz-groupware/backend into jane

[33mcommit 9ac05fd70efc42b750eb03e7b81c27d726e0b2c0[m[33m ([m[1;32mdev[m[33m)[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Tue Sep 5 11:36:59 2023 +0900

    .

[33mcommit 37ac0a494852edd5b545ef01607ce08882d28a4d[m
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Tue Sep 5 11:36:35 2023 +0900

    [style]: 공백 처리변경

[33mcommit 1fad4ca94dba59ad88ad1a8a15e2453cf89d7fce[m
Merge: 822062f b633e0d
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Tue Sep 5 09:55:23 2023 +0900

    Merge pull request #7 from dz-groupware/jane
    
    [refactor]: 회사관리페이지 로직 변경

[33mcommit b633e0d86613392f9197ab480f9c9e0026ead355[m
Merge: 6d64218 822062f
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Tue Sep 5 09:55:14 2023 +0900

    Merge branch 'dev' into jane

[33mcommit 822062f3f1d755ba776360d1d97ba0b6bc9be20c[m
Merge: 852422a 8f24884
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Tue Sep 5 09:51:16 2023 +0900

    Merge pull request #5 from dz-groupware/kimaenzu
    
    메뉴 검색 API 기능 추가 및 수정

[33mcommit 6d64218aa8e0b944a5527efdd0e8672f16a5e630[m
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Mon Sep 4 20:43:30 2023 +0900

    [refator]:회사관리출력로직변경

[33mcommit 852422afbfc5310dc62798e02fb2844f497e3558[m
Merge: d0491ec a292f37
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Mon Sep 4 20:08:48 2023 +0900

    Merge pull request #6 from dz-groupware/subo
    
    [feat]: 권한 유저리스트 조회 기능 완성

[33mcommit a292f37b4eeb921931a9a35d640a8aedd583b81e[m
Author: subo <kws3363@gmail.com>
Date:   Mon Sep 4 19:58:50 2023 +0900

    [feat]: 권한 유저리스트 조회 기능 완성

[33mcommit 8f24884422dbf448b7feacb0b38953bfc84267cd[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Mon Sep 4 10:46:42 2023 +0900

    [feat] : 조직도 팝업 검색 API 기능 추가 및 수정
    
    - 전체 검색 기능 추가
    - 부서명/사원명 검색 기능 수정

[33mcommit d0491ecbe8359bab76703dbbe45cba6baa464b6e[m
Merge: e053452 ae8f6cd
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Sat Sep 2 18:56:23 2023 +0900

    Merge pull request #3 from dz-groupware/subo
    
    [feat]: 권한조회시 메뉴리스트 가져오기

[33mcommit ae8f6cda1691824ed39208b8bb63714c5bf0382b[m[33m ([m[1;31morigin/subo[m[33m)[m
Author: subo <kws3363@gmail.com>
Date:   Sat Sep 2 18:53:43 2023 +0900

    [feat]: 권한조회시 메뉴리스트 가져오기

[33mcommit 7655c023bd48f611434c9f518419c8dbc6e92ffa[m[33m ([m[1;31morigin/jane[m[33m)[m
Author: JE IN KIM <wpdls4332@gmail.com>
Date:   Thu Aug 31 20:50:12 2023 +0900

    [feat]: 회사관리페이지 완성
    
    <body>
    
    <footer>

[33mcommit 7e7bd7fd2d226342af4bd4447ff2a8fdafb0d791[m
Merge: 6bce728 42edcea
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Aug 31 16:20:41 2023 +0900

    Merge branch 'kimaenzu' of github.com:dz-groupware/backend into kimaenzu

[33mcommit 6bce7280db5d03d04a949e0d6cce810aac7b307d[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Aug 31 16:10:43 2023 +0900

    [refactor]
    
    - 컨벤션에 맞게 수정
    - 불필요한 코드 삭제

[33mcommit e053452bef5c57885d38dce0cfddecc8c34c6006[m
Merge: a5cff8d 42edcea
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Wed Aug 30 21:11:41 2023 +0900

    Merge pull request #2 from dz-groupware/kimaenzu
    
    Kimaenzu

[33mcommit 42edcea0a415727f1ee406eafa7f4ea08aa7f00b[m
Merge: c583d97 a5cff8d
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Wed Aug 30 21:10:26 2023 +0900

    Merge branch 'dev' into kimaenzu

[33mcommit c583d973ec4fae0fc247a4baccaf072be4771290[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Wed Aug 30 20:50:01 2023 +0900

    [feat]: 메뉴 설정 페이지 메뉴 추가 API
    
    - 대메뉴/메뉴 추가 기능

[33mcommit 55fc7fa62f308fcfce677a61e4f71910993a01ea[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Tue Aug 29 23:07:05 2023 +0900

    [feat]: API 수정

[33mcommit a5cff8d7b23ea69cee1b51082ec6c523053fa92c[m
Merge: a632777 8f7a542
Author: subo <82173071+subo-9439@users.noreply.github.com>
Date:   Mon Aug 28 19:05:45 2023 +0900

    Merge pull request #1 from dz-groupware/subo
    
    [feat] 로그인 기능 구현

[33mcommit f191abd1080718f06d21be09deb919eb2318286d[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Fri Aug 25 17:48:03 2023 +0900

    [fix]: mapper.xml 수정 및 이미지 저장 방식 수정
    
    iconFile 저장
    - 경로 : resources > image >
    이미지 파일 명으로 저장. (중복 이름일 경우 덮어쓰기 됨)

[33mcommit 406f307d293528736990b9509f9d4b4c1f4d6417[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Aug 24 23:10:03 2023 +0900

    [feat]: 메뉴 설정 페이지 API
    
    - 메뉴 선택 시 메뉴 상세 정보 가져오기
    - 메뉴 추가 : 데이터 / 이미지

[33mcommit 62ad56e4e0f59929e2b3a7ffdc5ce494a1ed4b9c[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Thu Aug 24 23:07:41 2023 +0900

    [feat]: 메뉴 설정 페이지 API
    
    - 메뉴 선택 시 상세 정보 가져오기
    - 메뉴 검색 : 메뉴 명으로 검색
    - 메뉴 추가 : 데이터 / 이미지
    
    <body>
    
    <footer>

[33mcommit 4628f6b70ed25aae6c793a5a81f8a2e28c6041da[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Wed Aug 23 23:42:55 2023 +0900

    [feat]: 팝업 API 수정
    
    - 조직도 팝업
    : 사원리스트 관련 수정

[33mcommit 89a860d02cc9e20d4e98a967f68053cd33e762cf[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Wed Aug 23 15:36:51 2023 +0900

    [feat]: 팝업 API 추가
    
    - 프로필 API
    : 현재 ID가 가진 모든 소속 프로필 정보를 가져온다.
    
    - 부서트리 API
    : type을 파라미터로 받아, 요청에 맞는 매퍼를 사용하여 데이터를 가져온다.
    
    - 사원리스트 API
    : type을 파라미터로 받아, 요청에 맞는 매퍼를 사용하여 데이터를 가져온다.

[33mcommit 4819f41f7ff110cb0d252834885dd047c1febc29[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Wed Aug 23 15:31:03 2023 +0900

    [feat]: 팝업 API (프로필, 부서 조직도)
    
    - 프로필 팝업 API
    : 현재 사원의 모든 프로필 정보를 가져온다.
    
    - 부서 조직도 API
    : type을 구분해 요청에 맞는 매퍼로 데이터를 가져온다.
    
    - 사원 리스트 API
    : type을 구분해 요청에 맞는 매퍼로 데이터를 가져온다.

[33mcommit 8f7a542e102861a57afbe33f41b1c24ee18d6f8f[m
Author: subo <kws3363@gmail.com>
Date:   Wed Aug 23 14:50:24 2023 +0900

    [feat]: 커서기반 페이지네이션 api 구현
    
    기존 페이지 네이션 에서 커서기반 페이지네이션으로 변경
    
    <footer>

[33mcommit 27c284c703d63956381c014f3c2f60ca3c83d66a[m[33m ([m[1;32mmain[m[33m)[m
Author: khj5606kh@gmail.com <khj5606kh@gmail.com>
Date:   Tue Aug 22 21:30:13 2023 +0900

    [feat]: GNB API
    
    - GNB 메뉴 가져오기
    : 사원ID 에 맞는 메뉴 리스트 가져오기
    
    - 즐겨찾기 메뉴 가져오기
    : 사원ID에 맞는 즐겨찾기 리스트 가져오기
    
    - 즐겨찾기 삭제
    : 사원ID와 메뉴ID를 받아서 즐겨찾기 삭제

[33mcommit a0ffb1d8342accc604f5c161b38b730547e38642[m
Author: subo <kws3363@gmail.com>
Date:   Tue Aug 22 13:26:49 2023 +0900

    [refactor]: 페이지네이션 , MVC 메서드 작명 컨벤션 변경

[33mcommit 41d8e77751308e553a2f3417bc688a204b6676a2[m
Author: subo <kws3363@gmail.com>
Date:   Mon Aug 21 21:50:54 2023 +0900

    [refactor]: 코드 컨벤션을 위한 코드 수정.
    
    ResponseEntity 추가.
    아키텍처 구조 도메인기반으로 변경.
    dto 명칭 규칙 req,res 적용
    공통dto 생성
    - SingleResponseDto - 하나의 데이터
    - MultiResponseDto - 페이지네이션으로 가져온 데이터, 페이지 정보

[33mcommit 499a8724438924d76c6e7802f942aebbbfaa2489[m
Author: subo <kws3363@gmail.com>
Date:   Mon Aug 21 14:38:59 2023 +0900

    [feat]: jwt access provider 완성
    
    프론트에 데이터 전달 확인

[33mcommit a63277752e9c65d8dee942a17429db2c409a0d36[m[33m ([m[1;31morigin/main[m[33m, [m[1;31morigin/HEAD[m[33m)[m
Author: subo <kws3363@gmail.com>
Date:   Mon Aug 21 09:36:26 2023 +0900

    [chore]: 백엔드 프로젝트 생성
    
    레포지토리 구조 변경
    프로젝트/백엔드,프론트 => 프론트 , 백엔드
