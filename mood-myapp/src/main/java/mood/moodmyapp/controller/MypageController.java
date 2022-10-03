package mood.moodmyapp.controller;


import mood.moodmyapp.Session.SessionConstant;
import mood.moodmyapp.common.KakaoOauthService;
import mood.moodmyapp.domain.Friend;
import mood.moodmyapp.domain.Member;
import mood.moodmyapp.service.MypageService;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RequestMapping(value="/mypage")
@Controller
public class MypageController {



    @Autowired
    MypageService mypageService;

    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    /**
     * 마이페이지 메인
     * @param
     */
    @GetMapping(value="/main.do")
    public String mypageMain(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(false);
        //비로그인 상태면(세션이 없다면) 로그인페이지로 이동
        if(session == null){
            return "redirect:/login/login.do";
        }
        //로그인한 상태라면
        // 세션에 저장된 회원 조회
        String isMember = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);

        // 세션에 회원 데이터가 없으면 홈으로 이동
        if(isMember == null){
            return "redirect:/login/login.do";
        }

        //세션이 있다면 마이페이지 메인으로 이동
        model.addAttribute("member", isMember);
        return "/mypage/main";

    }


    /**
     * 내 친구목록 조회하기
     */

    @GetMapping(value = "/friendList.do")
    public String friendList (HttpServletRequest request, Model model){

        HttpSession session = request.getSession(true);
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);
        List<Friend> friendList = mypageService.findAllByUserId(userId);
        model.addAttribute("friendList",friendList);

        return "/mypage/friendList";
    }

    /**
     * 친구 찾기
     */
    @ResponseBody
    @GetMapping(value = "/searchFriend.do")
    public Map<String,String> searchFriend(String userId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userId = request.getParameter("userId");

        HttpSession session = request.getSession(true);
        String myId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);

        System.out.println("Controller 친구찾기 userId : " + userId);
        System.out.println("Controller 내아이디 myId : " + myId);

        Optional isFriend = mypageService.existFriend(userId,myId);

        // 자기자신을 친구로 조회할 경우
        if(userId.equals(myId)){

            HashMap<String,String> errorCode =  new HashMap<String,String>();
            String code = "자기자신은 친구 추가할 수 없습니다";
            errorCode.put("code",code);
            return errorCode;

        }else if(isFriend.isPresent()){
            HashMap<String,String> errorCode =  new HashMap<String,String>();
            String code = "이미 추가된 친구입니다.";
            errorCode.put("code",code);
            return errorCode;
        }else{
            HashMap<String,String> foundFriend =  new HashMap<String,String>();
            String friend = mypageService.searchFriend(userId);
            foundFriend.put("friend",friend);


            JSONObject json = new JSONObject(foundFriend);
            System.out.println(String.valueOf(json));

            return foundFriend;
        }

    }



    /**
     * 친구 추가하기
     */

    @ResponseBody
    @PostMapping(value="/addFriend.do")
    public Map<String,String> makeFriend(String friendIdRaw,HttpServletRequest request, @RequestBody HashMap<String, String>friendMap){
         String friendId = friendMap.get("friendIdRaw");
        //String friendId = request.getParameter(friendId);
        System.out.println("this is controller!!!!!!friendId : " + friendId);

        HttpSession session = request.getSession(true);
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);
        System.out.println("this is controller!!!!!!userId : " + userId);
        mypageService.makeFriend(userId, friendId);

        friendMap.put("friendId",friendId);
        return friendMap;

    }

}
