package mood.moodmyapp.controller;

import mood.moodmyapp.domain.Member;
import mood.moodmyapp.service.LoginService;
import mood.moodmyapp.session.SessionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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


        // 로그인 성공
        if(loginService.login(member)){
            boolean loginMember = loginService.login(member);
            HttpSession session = request.getSession(); //세션이 있으면 세션 반환, 없으면 신규 세션을 생성하여 반환
            session.setAttribute(SessionConstant.LOGIN_MEMBER,loginMember); //이름, 값을 인자로 세션값 바인딩

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
        System.out.println("로그아웃 성공");
        return "redirect:/";
    }

}
