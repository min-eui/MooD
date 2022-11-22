package mood.moodmyapp.controller;

import mood.moodmyapp.Session.SessionConstant;
import mood.moodmyapp.domain.Reply;
import mood.moodmyapp.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    /**
     * 댓글 등록하기
     * @param request
     * @param replyForm
     * @return
     */
    @PostMapping("/reply/addReply.do")
    public String saveReply(HttpServletRequest request, Reply replyForm){
        HttpSession session = request.getSession(false);
        //비로그인 상태면(세션이 없다면) 로그인페이지로 이동
        if(session == null){
            return "redirect:/login/login.do";
        }
        //로그인한 상태라면
        // 세션에 저장된 회원 조회
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);

        // 세션에 회원 데이터가 없으면 홈으로 이동
        if(userId == null){
            return "redirect:/login/login.do";
        }

        //세션에서 댓글작성자 아이디 넣어주기
        replyForm.setRegistrant(userId);

        Long moodNum = replyForm.getMoodNum();
        // null체크 나중에 추가
        replyService.saveReply(replyForm);

        return "redirect:/mood/detail/"+moodNum;
    }


    /**
     * 댓글 삭제하기
     */
    @ResponseBody
    @PostMapping(value ="/reply/deleteReply.do", produces = "text/plain;charset=utf-8")
    public void deleteReplyProc(HttpServletRequest request, @RequestParam(value = "replyNum") String replyNum){

        Long replyNumPars = Long.parseLong(replyNum);

        /* 본인 글만 삭제할 수 있다. 로그인> 세션에서 아이디 체크 > 아이디에 쓴 댓글에만 삭제수정버튼 노출*/
        /* 어차피 지우면 안되는 글은 버튼이 없을테니까 삭제버튼만 구현하자 */
        int isDel = replyService.deleteByReplyNum(replyNumPars);

    }

}
