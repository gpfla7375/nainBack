package io.paioneer.nain.interview.controller;

import io.paioneer.nain.interview.jpa.entity.InterviewEntity;
import io.paioneer.nain.interview.model.dto.InterviewDto;
import io.paioneer.nain.interview.model.service.InterviewService;
import io.paioneer.nain.member.model.dto.MemberDto;
import io.paioneer.nain.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/interview")
@CrossOrigin
public class InterviewController {

    private final InterviewService interviewService;
    private final MemberService memberService;

    //itvNo 생성을 위한 title, memberNo(memberDto) insert
    @PostMapping
    public ResponseEntity<Long> insertInterview(@RequestParam(name="memberNo") Long memberNo, @RequestParam(name="title") String title) {

        MemberDto loginMember = memberService.findById(memberNo);
        InterviewDto interviewDto = new InterviewDto();
        interviewDto.setTitle(title);
        interviewDto.setMemberNo(memberNo);
        interviewDto.setMemberDto(loginMember);
        return new ResponseEntity<>(interviewService.insertInterview(interviewDto), HttpStatus.OK);
    }

    //면접 기록 목록 조회
    @GetMapping("/list")
    public ResponseEntity<Page<InterviewEntity>> selectInterviewList(@RequestParam(name="page") int page,
                                                                  @RequestParam(name="size") int size, @RequestParam(name="memberNo") String memberNo) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InterviewEntity> interview = interviewService.selectInterviewList(Long.parseLong(memberNo), pageable);
        return new ResponseEntity<>(interview, HttpStatus.OK);
    }

    //면접 기록 삭제
    @DeleteMapping("/list")
    public ResponseEntity<?> deleteInterview(@RequestParam(name="itvNo") Long itvNo){
        log.info(itvNo.toString());
        interviewService.deleteInterview(itvNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //면접 질문 출력
    @GetMapping("/question")
    public ResponseEntity<?> selectQuestion(){
        ArrayList list = interviewService.getRandomQuestion();
        log.info(list.toString());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
