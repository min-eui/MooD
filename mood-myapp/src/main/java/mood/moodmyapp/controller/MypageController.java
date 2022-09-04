package mood.moodmyapp.controller;

import mood.moodmyapp.Session.SessionConstants;
import mood.moodmyapp.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping(value = "/mypage")
@Controller
public class MypageController {

    @GetMapping("/home.do")
    public String myPageHome(HttpServletRequest request, Model model){

        //세션이 없으면 로그인 폼으로 이동
        HttpSession session = request.getSession(false);
        if(session == null){
            return "/login/login";
        }

        //세션에 저장된 회원 조회

        boolean loginMember = (boolean) session.getAttribute(SessionConstants.LOGIN_MEMBER);

        //세션에 회원 데이터가 없으면 로그인 폼으로 이동
        if(loginMember == false){
            return "/login/login";
        }

        // 세션이 유지되면 홈으로 이동
        model.addAttribute("member",loginMember);
        return "/mypage/home";
    }

}
