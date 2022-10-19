package mood.moodmyapp.controller;

import mood.moodmyapp.Session.SessionConstant;
import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MoodController {


    private final MoodService moodService;

    @Autowired
    public MoodController(MoodService moodService) {
        this.moodService = moodService;
    }


    /**
     * 글쓰기 페이지
     */
    @GetMapping("/mood/write.do")
    public String write(HttpServletRequest request){
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


        return "/mood/writeForm";
    }


    /**
     * 글쓰기 처리
     */
    @PostMapping("/mood/write.do")
    public String writeProc(Mood moodForm, HttpServletRequest request){

        HttpSession session = request.getSession(false);

        //비로그인 상태면(세션이 없다면) 로그인페이지로 이동
        if(session == null){
            return "redirect:/login/login.do";
        }
        //로그인한 상태라면
        // 세션에 저장된 회원 조회
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);

        // 세션에서 글 작성자를 userId로 세팅
        moodForm.setUserId(userId);
        Mood isSaved = moodService.saveMood(moodForm);
        HashMap<String,String> map = new HashMap();
        if(isSaved==null){
            map.put("msg","글쓰기 실패");
        }else{
            map.put("msg","글쓰기 성공");
        }
        return "redirect:/";
    }

    /**
     * 메인 페이지
     * @param model
     * @return
     */

    @GetMapping("/")
    public String mainPage(Model model){

        List<Mood> moodList = moodService.findAllMood();
        model.addAttribute("moodList",moodList);
        return "index";
    }

}
