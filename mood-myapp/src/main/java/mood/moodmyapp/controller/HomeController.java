package mood.moodmyapp.controller;

import mood.moodmyapp.Session.SessionConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model){
        HttpSession session;
        session = request.getSession(false);
        //로그인한 상태라면
        // 세션에 저장된 회원 조회
        String isMember = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);
        if(isMember.isEmpty()){
            model.addAttribute("isMember","null");
        }else{
            model.addAttribute("isMember",isMember);
        }

        return "index";
    }

}
