package mood.moodmyapp.controller;

import mood.moodmyapp.session.SessionConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping(value="/mypage")
@Controller
public class MypageController {

    @GetMapping(value="/main.do")
    public String mypageMain(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(false);
        //비로그인 상태면(세션이 없다면) 로그인페이지로 이동
        if(session == null){
            return "redirect:/login/login.do";
        }
        //로그인한 상태라면
        // 세션에 저장된 회원 조회
        Boolean loginMember = (Boolean)session.getAttribute(SessionConstant.LOGIN_MEMBER);

        // 세션에 회원 데이터가 없으면 홈으로 이동
        if(loginMember == null){
            return "redirect:/login/login.do";
        }

        //세션이 있다면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "/mypage/main";
    }

}
