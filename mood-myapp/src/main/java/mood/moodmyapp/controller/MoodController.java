package mood.moodmyapp.controller;

import mood.moodmyapp.Session.SessionConstant;
import mood.moodmyapp.common.FileStore;
import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.domain.MoodForm;
import mood.moodmyapp.domain.Reply;
import mood.moodmyapp.service.MoodService;
import mood.moodmyapp.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

@Controller
public class MoodController {


    private final MoodService moodService;
    private final FileStore fileStore;
    private final ReplyService replyService;

    @Autowired
    public MoodController(MoodService moodService, FileStore fileStore, ReplyService replyService) {
        this.moodService = moodService;
        this.fileStore = fileStore;
        this.replyService = replyService;
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

    @RequestMapping (value="/mood/write.do")
    public String writeProc(@ModelAttribute("moodForm") MoodForm moodForm, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession(false);


        //비로그인 상태면(세션이 없다면) 로그인페이지로 이동
        if(session == null){
            return "redirect:/login/login.do";
        }
        // mood값 세팅할 객체 선언
//        Mood mood = new Mood();

        //로그인한 상태라면
        // 세션에 저장된 회원 조회
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);

        // 세션에서 글 작성자를 userId로 세팅
//        mood.setUserId(userId);
        moodForm.setUserId(userId);
        //moodForm에서 multifile타입으로 받아온 이미지 uploadFile타입으로 변환
        //List<UploadFile> storeImageFiles = fileStore.storeFiles(moodForm.getImagesFiles());

        //데이터베이스에 저장
//        mood.setImagesFiles(storeImageFiles);


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
        return "/index";
    }


    /**
     * 상세 페이지
     * @param
     * @return
     */
    @GetMapping("/mood/detail/{moodNum}")
    public String moodDetail(@PathVariable String moodNum, Model model) {

        Long  moodNumL= Long.parseLong(moodNum);
        Mood moodPage = moodService.findByMoodNum(moodNumL);
        List<Reply> replyList = replyService.findAllByMoodNum(moodNumL);
        model.addAttribute("moodPage",moodPage);
        model.addAttribute("replyList",replyList);
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
//            code = "글 삭제에 성공했습니다.";
//
//        }else {
//            code = "글 삭제에 실패했습니다.";
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
    public String moodUpdateProc(MoodForm moodForm) throws IOException {

        Mood isUpdate = moodService.saveMood(moodForm);
        System.out.println("업데이트 성공 : " + isUpdate);
        return "redirect:/";
    }

    /**
     * 글에서 이미지 보여주기
     */
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }


    /**
     * 좋아요 처리
     */
    @ResponseBody
    @PostMapping("/mood/like.do")
    public String likeProc(@RequestParam(name = "moodNum") String moodNum, @RequestParam(name = "isLike") int isLike, HttpServletRequest request){

        Long  moodNumL= Long.parseLong(moodNum);
        HttpSession session = request.getSession(false);

//        IsLikeDto islike = new IsLikeDto();
        //비로그인 상태면(세션이 없다면) 로그인페이지로 이동
        if(session == null){
            return "로그인후 이용해주세요.";
        }

        //로그인한 상태라면
        // 세션에 저장된 회원 조회
        String likeUserId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);

       // 세션에서 좋아요 클릭한 사람을 userId로 세팅

        System.out.println("###############MoodController MoodNum = "+moodNumL);
        moodService.likeProc(moodNumL,isLike,likeUserId);
        return "1";
    }

    /**
     * 좋아요 취소
     */
    @ResponseBody
    @PostMapping("/mood/likeCancel.do")
    public String likeCancel(@RequestParam(name="moodNum") String moodNum, @RequestParam(name = "isLike") int isLike, HttpServletRequest request){

        Long  moodNumL= Long.parseLong(moodNum);
        HttpSession session = request.getSession(false);

//        IsLikeDto islike = new IsLikeDto();
        //비로그인 상태면(세션이 없다면) 로그인페이지로 이동
        if(session == null){
            return "로그인후 이용해주세요.";
        }
        //로그인한 상태라면
        // 세션에 저장된 회원 조회
        String likeUserId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);

        moodService.likeCancel(moodNumL,isLike,likeUserId);
        System.out.println("좋아요취소moodNumL : " + moodNumL);
        return "0";
    }

    /**
     * 통계 페이지
     */
    @GetMapping("/mood/statics/moodStatics.do")
    public String moodStatics(HttpServletRequest request){
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
        return "/mood/moodStatics";
    }

    /**
     * 월별 감정 통계 데이터 조회
     */
    @ResponseBody
    @PostMapping("/mood/statics/monthly/EmotionDataType.do")
    public List<List> emotionMonthlyStatics (HttpServletRequest request, String searchYear){

        HttpSession session = request.getSession(false);
        //통계페이지에서 로그인 체크를 해서 로그인한 상태
        // 세션에 저장된 회원 조회
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);

        List<List> staticMList = moodService.findMonthlyStatics(searchYear, userId);
        return staticMList;
    }

//    /**
//     * 년도별 감정 통계 데이터 조회
//     */
//    @ResponseBody
//    @PostMapping("/mood/statics/yearly/EmotionDataType.do")
//    public List<List> emotionYearlyStatic(String searchYear) {
//        List<List> staticYList = moodService.findYearlyStatics(searchYear);
//        return staticYList;
//    }

    /**
     * 글 검색 페이지
     */
    @GetMapping("/mood/search.do")
    public String searchKeyword(String keyword, Model model){
        List<Mood> searchList = moodService.searchKeyword(keyword);
        //model.addAttribute("keyword",keyword);
        model.addAttribute("searchList",searchList);
        return "/mood/searchPage";
    }



}
