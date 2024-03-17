package org.example.test1.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.example.test1.Jwt.Blacklist;
import org.example.test1.Jwt.BlacklistRepository;
import org.example.test1.Jwt.JwtFunc;
import org.example.test1.domain.Challenge;
import org.example.test1.domain.Challenger;
import org.example.test1.domain.Diary;
import org.example.test1.domain.ck.ChallengerId;
import org.example.test1.dto.ChallengePagingResponse;
import org.example.test1.dto.UpdateDiary;
import org.example.test1.dto.UpdateTag;
import org.example.test1.exception.InvalidJwtTokenException;
import org.example.test1.repository.ChallengeRepository;
import org.example.test1.repository.ChallengerRepository;
import org.example.test1.repository.DiaryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ChallengeController {
    private ChallengeRepository challengeRepository;
    private ChallengerRepository challengerRepository;
    private DiaryRepository diaryRepository;

    private BlacklistRepository blacklistRepository;
    private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);


    public ChallengeController(ChallengeRepository challengeRepository, ChallengerRepository challengerRepository, DiaryRepository diaryRepository, BlacklistRepository blacklistRepository) {
        this.challengeRepository = challengeRepository;
        this.challengerRepository = challengerRepository;
        this.diaryRepository = diaryRepository;
        this.blacklistRepository = blacklistRepository;
    }

    //1-1.챌린지 생성(O)
    //1-2.챌린지 조회(O)- pagination(O)
    //1-3.챌린지 수정(O)
    //1-4.챌린지 삭제(수정필요)

    //2-1.챌린지 참여(O)
    //2-2.챌린지 참여취소(O)

    //3-1.하루일기 생성(O)
    //3-2.하루일기 조회(O)
    //3-3.하루일기 수정(O)
    //3-4.하루일기 삭제(O)


    //1-1.챌린지 생성
    @PostMapping("/challenge")
    public ResponseEntity<Map<String, Object>> createChallenge(@RequestBody Challenge challenge) {
        Challenge savedChallenge = challengeRepository.save(challenge);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "The challenge was successfully created.");
        response.put("challenge", savedChallenge);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //1-2.챌린지 조회
    @GetMapping("/challenge")
    public ResponseEntity<ChallengePagingResponse> retrieveChallenges(
            // page와 size를 파라미터로 받음 (page 디폴트값: 1, size 디폴트값: 10)
    		@RequestParam(defaultValue = "1") int page, 
    		@RequestParam(defaultValue = "10") int size,
    		@RequestParam(required = false) Integer category,
            @RequestParam(required = false) String search) {
        	
    	// 페이지네이션 정보 설정
    	Pageable pageable = PageRequest.of(page - 1, size); // JPA는 페이지를 0부터 시작하므로 page - 1 필요함
    	
    	// 페이지네이션된 챌린지 목록 선언
        Page<Challenge> challengePage;
        
        if (search != null && !search.trim().isEmpty()) {
        	// 검색어가 있는 경우, 검색어로 챌린지 조회
        	logger.info("Received search query: {}", search);
        	challengePage = challengeRepository.findByKeyword(search, pageable);
        	//challengePage = challengeRepository.findByTagNameContainingOrTagDescContaining(search, search, pageable);
        }
        else if (category != null) {
        	// 카테고리가 지정된 경우, 해당 카테고리의 챌린지 조회
        	challengePage = challengeRepository.findByCategory(category, pageable);
        }
        else {
        	// 카테고리나 검색어가 없는 경우, 전체 챌린지 조회
        	challengePage = challengeRepository.findAll(pageable);
        }
        
        // 조회된 데이터를 바탕으로 응답 DTO 객체 생성
        ChallengePagingResponse response = new ChallengePagingResponse();
        response.setPage(page); // 현재 페이지 번호
        response.setSize(size); // 페이지당 아이템 수
        response.setTotalPages(challengePage.getTotalPages()); // 총 페이지 수
        response.setTotalCount(challengePage.getTotalElements()); // 총 아이템 수
        response.setChallenges(challengePage.getContent()); // 현재 페이지의 챌린지 목록

        // DTO 반환
        return ResponseEntity.ok(response);
    }


    //1-3.챌린지 수정
    @PutMapping("/challenge/{noTag}")
    public ResponseEntity<String> updateChallenge(@PathVariable Long noTag, @RequestBody UpdateTag newChall) {

        Optional<Challenge> challOptional = challengeRepository.findById(noTag);

        if (challOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("태그가 존재하지 않습니다.");
        }

        Challenge existingChall = challOptional.get();

        // 업데이트는 tagName, tagDesc, tagImg만 가능
        //(바꿀 것이 있을 때만 수정해야 함. 아니면 null로 업데이트 될 수 있음)
        if(newChall.getTagName() != null) existingChall.setTagName(newChall.getTagName());
        if(newChall.getTagImg() != null) existingChall.setTagImg(newChall.getTagImg());
        if(newChall.getTagDesc() != null) existingChall.setTagDesc(newChall.getTagDesc());

        challengeRepository.save(existingChall); // 수정된 정보 저장

        return ResponseEntity.ok("The challenge was successfully modified.");
    }

    //1-4.챌린지 삭제
    @DeleteMapping("/challenge")
    public ResponseEntity<String> deleteChallenge(@RequestParam Long noTag){
        challengeRepository.deleteById(noTag);
        //레포추가해서 user_id로 삭제


        return ResponseEntity.ok("delete tag.");
    }

    //2-1.챌린지 참여
    @PostMapping("/challenge/{noTag}/participant")
    public ResponseEntity<String> participant(@RequestHeader("Authorization") String token,
                                              @PathVariable Long noTag, @RequestParam String endDate) {

        JwtFunc jwt = new JwtFunc();
        String userId = jwt.unpackJWT(token);
        checkBlacklist(token);

        // 챌린지 태그 존재하지 않으면
        if (!challengeRepository.findById(noTag).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("태그 조회불가.");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date _endDate = null;

        try {
            _endDate = sdf.parse(endDate);
        } catch(Exception e) {}

        Date _startDate = java.sql.Date.valueOf(LocalDate.now());


        //end_date 날짜 검사해야 함.
        challengerRepository.save(new Challenger(noTag, userId, _startDate, _endDate));

        return ResponseEntity.ok("챌린지에 참여하셨습니다!");
    }

    //2-2.챌린지 참여취소
    @DeleteMapping("/challenge/{noTag}/participant")
    public ResponseEntity<String> unparticipant(@RequestHeader("Authorization") String token,
                                         @PathVariable Long noTag) {
        JwtFunc jwt = new JwtFunc();
        String userId = jwt.unpackJWT(token);
        checkBlacklist(token);

        challengerRepository.deleteById( new ChallengerId(noTag, userId) );

        return ResponseEntity.status(HttpStatus.OK).body("챌린지 참가 취소되었습니다.");
    }


    //3-1.하루일기 생성
    @PostMapping("/diary")
    public ResponseEntity<String> createDiary(@RequestHeader("Authorization") String token,
                                              @RequestParam Long noTag,
                                              @RequestBody Diary diary) {

        JwtFunc jwt = new JwtFunc();
        String user_id = jwt.unpackJWT(token);
        checkBlacklist(token);

        //컨텐츠, 이미지(옵션)만 받아오고 나머지를 객체에 채워 저장한다.
        diary.setUserId(user_id);
        diary.setNoTag(noTag);
        Date now = java.sql.Date.valueOf(LocalDate.now());
        diary.setWriteDate(now); //현재날짜

        diaryRepository.save(diary);

        return ResponseEntity.status(HttpStatus.CREATED).body("하루 일기 작성되었습니다.");
    }

    //3-2.하루일기 조회
    @GetMapping("/diary")
    public List<Diary> retrieveDiary(@RequestHeader("Authorization") String token,
                                     @RequestParam(required = false) Long noTag) {

        JwtFunc jwt = new JwtFunc();
        String user_id = jwt.unpackJWT(token);
        checkBlacklist(token);

        //전달된 파라미터가 없을 경우 전체 조회
        if(noTag == null) return diaryRepository.findByUserId(user_id);

        //파라미터 주면 태그 넘버와 유저 아이디로 조회
        return diaryRepository.findByUserIdAndNoTag(user_id, noTag);
    }

    //3-3.하루일기 수정
    @PutMapping("/diary")
    public ResponseEntity<String> updateDiary(@RequestHeader("Authorization") String token,
                                              @RequestBody UpdateDiary updateDiary) {

        JwtFunc jwt = new JwtFunc();
        String user_id = jwt.unpackJWT(token);
        checkBlacklist(token);

        Optional<Diary> diaryOptional = diaryRepository.findById(updateDiary.getDiaryId());

        if (diaryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("하루일기가 존재하지 않습니다.");
        }

        Diary existingDiary = diaryOptional.get();

        // 하루일기 내용과 이미지만 수정가능.
        //(바꿀 것이 있을 때만 수정해야 함. 아니면 null로 업데이트 될 수 있음)
        if(updateDiary.getContent() != null) existingDiary.setContent(updateDiary.getContent());
        if(updateDiary.getDiaryImg() != null) existingDiary.setDiaryImg(updateDiary.getDiaryImg());

        diaryRepository.save(existingDiary); // 수정된 정보 저장

        return ResponseEntity.ok("하루일기가 수정되었습니다.");
    }

    //3-4.하루일기 삭제
    @DeleteMapping("/diary")
    public ResponseEntity<String> deleteDiary(@RequestHeader("Authorization") String token,
                                              @RequestParam Long diaryId) {
        JwtFunc jwt = new JwtFunc();
        String user_id = jwt.unpackJWT(token);
        checkBlacklist(token);

        diaryRepository.deleteById(diaryId);
        return ResponseEntity.ok("하루일기를 삭제했습니다.");
    }

    //블랙리스트
    public void checkBlacklist(String token) {
        blacklistRepository.deleteByDeleteAtBefore(new Date());
        Optional<Blacklist> blacklistOptional = blacklistRepository.findById(token);

        //블랙리스트에 있으면 유효하지 않은 토큰.
        if(blacklistOptional.isPresent()) throw new InvalidJwtTokenException("유효하지 않은 토큰입니다.");
    }
}
