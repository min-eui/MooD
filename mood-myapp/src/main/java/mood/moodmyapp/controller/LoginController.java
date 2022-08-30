package mood.moodmyapp.controller;

import mood.moodmyapp.domain.Member;
import mood.moodmyapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.enterprise.inject.Model;

@Controller
@RequestMapping(value="/login")
public class LoginController {

    @Autowired
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login.do")
    public String loginForm(){
        return "/login/login";
    }


    @PostMapping("/login.do")
    public String loginProc(@ModelAttribute Member member){

        if(loginService.login(member)){
            return "redirect:/";
        }
        return "/login/login";
    }

}
