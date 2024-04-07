![header](https://capsule-render.vercel.app/api?type=waving&color=auto&height=300&section=header&text=Mingle&fontSize=90)

# 프로젝트 소개
 Mingle - 커뮤니티 중심의 Todo/ Challenge 공유 서비스

![mingle_icon](https://github.com/Todo-WebApp-Project/server/assets/99193939/27369ead-2322-4927-9bc5-4d1b95caf0a0)

- Mingle(”섞이다”)은  할 일 관리와 하루 챌린지의 독특한 조화를 통해 개인 성장과 발전을 촉진하는 커뮤니티 중심 플랫폼입니다.
- Mingle은 사용자들이 보다 효율적으로 하루 일정을 관리하며 자신만의 루틴을 구축하고  다른 이들과 함께 여정을 공유하며 발전할 수 있는 공간을 제공합니다.

# 팀 소개

<table>
	<thead>
    		<td colspan="4">BackEnd</td>
		<td colspan="1">FrontEnd</td>
	</thead>
	<tbody>
  	<tr>
    		<td>김다은</td>
		<td>김민정</td>
		<td>김홍주</td>
		<td>조윤상</td>
		<td>박재민</td>
  	</tr>
   	<tr>
    		<td><a href="https://github.com/fdaksjfj">@woody432</a></td>
		<td><a href="https://github.com/Mingguriguri">@Mingguriguri</a></td>
		<td><a href="https://github.com/zaqquum">@zaqquum</a></td>
		<td><a href="https://github.com/fdaksjf">@fdaksjf</a></td>
		<td><a href="https://github.com/jamminP">@jamminP</a></td>
		
  	</tr>
</tbody>
</table>

# Stacks
## Environment
<div align="center">
	<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white" />
	<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white" />
	<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=Amazon aws&logoColor=white">
</div>

## Config
npm

## Development
<div align="center">
	<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
	<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=Java&logoColor=white">
	<img src="https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=Eclipse%20IDE&logoColor=white"><br>
	<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
	<img src="https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=Oracle&logoColor=white"><br>
	<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white"><br>
</div>


## Communication
<div align="center">
	[![Slack](https://img.shields.io/badge/slack-4A154B?style=for-the-		badge&logo=Slack&logoColor=white)](https://your-slack-link)
	[![Discord](https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)](https://your-discord-link)
	[![Jira](https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jira&logoColor=white)](https://your-jira-link)
	[![Notion](https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white)](https://rainy-journey-3a1.notion.site/DOTORY-	44cb97d1957a403bbe7970625783811d?pvs=4)
</div>


# 아키텍쳐

- 추후 추가 예정
#  DB

- ERD
  ![mingle_DB](https://github.com/Todo-WebApp-Project/server/assets/99193939/8ffcd370-30dc-4509-9722-14e1b085f727)


# 기능

## 유저

- 회원가입
- 로그인
- 회원정보 조회
- 회원정보 수정
- 비밀번호 수정
- 로그아웃
- 회원 탈퇴

## 커뮤니티

- 게시글(다른 유저의 todo) 조회
- 팔로우/팔로우 취소
- 팔로우 조회/팔로잉 조회
- 상태메시지
- 좋아요/좋아요 취소

## Schedule

- 애플리케이션에서 google calendar 일정 가져오기 버튼 누르면 사용자 인증 후 Google Calendar에 설정한 event를 DB로 저장.
- 사용자의 Schedule 조회, 생성, 수정, 삭제

## Todo

- 사용자의 Todo 조회, 생성, 수정, 삭제
- 특정 Todo 항목에 대해 완료 여부 조작

## 하루챌린지

- 챌린지 태그 CRUD(생성, 조회, 수정, 삭제)
- 참여 기간 정해서 챌린지 참여
- 챌린지 별 하루일기 CRUD(생성, 조회, 수정, 삭제)

## 성과 측정

업데이트 시간은 매일 00:00 를 기준으로 한다.

- 성과 기록
    - 사용자가 설정한 Todo를 모두 완료한 날짜를 기록
- Leveling  기능
    - 사용자의 Level 진행 상황 조회
    - Todo를 모두 완료한 횟수를 기준으로 사용자의 Level 측정 및 업데이트
        - 매일 00 시마다 사용자의  Level  업데이트 진행
        
  
 
# 역활 

## 김다은

- JWT를 사용하여 로그인시 access token 발급과 요청 시 인증 수단으로 이용
- 로그아웃 , 회원탈퇴 기능
- 하루일기 CRUD

## 김민정

- 유저 회원가입, 회원정보 조회/수정, 비번 수정, ~~구글 API 연동~~
- 챌린지 CRUD
- 커뮤니티 - 팔로우/팔로잉, 좋아요, 게시글 보여주기

## 김홍주

- Todo와 Schedule 조회, 생성, 수정, 삭제
- 특정 Todo 항목의 완료 여부 확인
- 성과 측정 - 성과 기록 기능

## 조윤상

- 애플리케이션에 Google Calendar API 연동
- 성과 측정 - Leveling 기능



## 개발기간

- 백엔드/ 프론트 엔드 개발 : 1/10~
- 배포 :
- 리팩토링 :
