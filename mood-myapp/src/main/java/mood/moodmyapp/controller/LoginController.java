package mood.moodmyapp.controller;

import mood.moodmyapp.Session.SessionConstants;
import mood.moodmyapp.domain.Member;
import mood.moodmyapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login/login.do")
    public String loginForm(){
        return "/login/login";
    }


    @PostMapping("/login/login.do")
    public String loginProc(@ModelAttribute Member member, HttpServletRequest request){

       boolean loginMember = loginService.login(member);
        //로그인 성공 처리
        if(loginService.login(member)){
            HttpSession session = request.getSession();// 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성하여 반환
            session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);// 세션에 로그인 회원 정보 보관

            return "redirect:/";
        }
        return "/login/login";
    }

    @PostMapping("/logout.do")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();   //세션 날림
        }
        return "redirect:/";
    }


}
