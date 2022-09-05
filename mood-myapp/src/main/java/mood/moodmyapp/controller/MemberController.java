package mood.moodmyapp.controller;

import mood.moodmyapp.domain.Member;
import mood.moodmyapp.repository.MemberRepository;
import mood.moodmyapp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import java.util.Optional;

@Controller
@RequestMapping(value= "/member")
public class MemberController {


    private final MemberService memberService;


    @Autowired  //맴버 컨트롤러가 생성이 될 때 스프링빈에 등록된 맴버서비스 주입
    public MemberController(MemberService memberService) {

        this.memberService = memberService;
    }

    @GetMapping("/join.do")
    public String memberJoinForm() {
        return "member/joinForm";
    }


    @PostMapping("/join.do")
    public String memberJoin(Member member) {

        memberService.join(member);
        String path = "/login/login.do";
        return "redirect:" + path;
    }

    @ResponseBody
    @PostMapping(value = "/checkId.do", produces = "text/plain;charset=utf-8")
    public String checkId(@RequestParam(value = "userId") String userId) {
        System.out.println("아이디 : " + userId);

        if (memberService.existByMemberId(userId)) {
            return "중복";
        } else {
            return "사용가능";
        }

    }

    @ResponseBody
    @PostMapping(value = "/checkNick.do", produces = "text/plain;charset=utf-8")
    public String checkNickName(@RequestParam(value = "nickName") String nickName) {
        System.out.println("아이디 : " + nickName);

        if (memberService.existByMemberNickName(nickName)) {
            return "중복";
        } else {
            return "사용가능";
        }
    }




}