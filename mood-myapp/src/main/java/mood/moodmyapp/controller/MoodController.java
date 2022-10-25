package mood.moodmyapp.controller;

import mood.moodmyapp.Session.SessionConstant;
import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//        moodForm.setMood(String.valueOf(mood));
//        System.out.println("##################mood" + moodForm.getMood());
//        moodForm.setMood(moodForm.getMood());

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


    /**
     * 상세 페이지
     * @param
     * @return
     */
    @GetMapping("/mood/detail/{moodNum}")
    public String moodDetail(@PathVariable String moodNum, Model model) {

        Long  MoodNumL= Long.parseLong(moodNum);
        Mood moodPage = moodService.findByMoodNum(MoodNumL);
        model.addAttribute("moodPage",moodPage);
        return "/mood/detail";
    }

    /**
     * 글 삭제 처리
     */

    @ResponseBody
    @PostMapping(value ="/mood/deleteMood.do", produces = "text/plain;charset=utf-8")
    public void deleteProc(HttpServletRequest request, @RequestParam(value = "moodNum") String moodNum){

        Long moodNumPars = Long.parseLong(moodNum);

        /* 본인 글만 삭제할 수 있다. 로그인> 세션에서 아이디 체크 > 아이디에 쓴 글에만 삭제수정버튼 노출*/
        /* 어차피 지우면 안되는 글은 버튼이 없을테니까 삭제버튼만 구현하자 */
        int isDel = moodService.deleteByMoodNum(moodNumPars);
//        HashMap<String,String> stateCode =  new HashMap<String,String>();
//        String code="";
//        if(isDel!=0){
//            code = "친구삭제에 성공했습니다.";
//
//        }else {
//            code = "친구삭제에 실패했습니다.";
//        }
//        return code;
    }

    /**
     * 글수정 페이지
     * @param
     * @return
     */
    @GetMapping("/mood/update/{moodNum}")
    public String moodUpdate(@PathVariable String moodNum, Model model) {

        Long  MoodNumL= Long.parseLong(moodNum);
        Mood moodPage = moodService.findByMoodNum(MoodNumL);
        model.addAttribute("moodPage",moodPage);
        return "/mood/update";
    }

    /**
     * 글 수정 처리
     */
    @PostMapping("/mood/update.do")
    public String moodUpdateProc(Mood moodForm){
//        System.out.println("############컨트롤러"+moodForm.getMoodNum());
        Mood isUpdate = moodService.saveMood(moodForm);
        System.out.println("업데이트 성공 : " + isUpdate);
        return "redirect:/";
    }

}
