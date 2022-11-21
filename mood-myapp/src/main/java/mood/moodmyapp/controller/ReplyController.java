package mood.moodmyapp.controller;

import mood.moodmyapp.domain.Reply;
import mood.moodmyapp.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/reply/addReply.do")
    public String saveReply(HttpServletRequest request, Reply replyForm){
//        HttpSession session = request.getSession(false);
//        //비로그인 상태면(세션이 없다면) 로그인페이지로 이동
//        if(session == null){
//            return "redirect:/login/login.do";
//        }
//        //로그인한 상태라면
//        // 세션에 저장된 회원 조회
//        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);
//
//        // 세션에 회원 데이터가 없으면 홈으로 이동
//        if(userId == null){
//            return "redirect:/login/login.do";
//        }

        Long moodNum = replyForm.getMoodNum();
        // null체크 나중에 추가
        replyService.saveReply(replyForm);

        return "redirect:/mood/detail/"+moodNum;
    }
}
