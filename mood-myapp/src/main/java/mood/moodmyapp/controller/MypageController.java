package mood.moodmyapp.controller;


import mood.moodmyapp.Session.SessionConstant;
import mood.moodmyapp.common.KakaoOauthService;
import mood.moodmyapp.domain.Friend;
import mood.moodmyapp.domain.Member;
import mood.moodmyapp.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    public String friendList (String userId, Model model){


        List friendList = mypageService.getMyFriendList(userId);
        model.addAttribute("friendList",friendList);

        return "/mypage/friendList";
    }

    /**
     * 친구 찾기
     */
    @ResponseBody
    @GetMapping(value = "/searchFriend.do")
    public List<Member> searchFriend(String userId, HttpServletRequest request){

        userId = request.getParameter("userId");
        System.out.println("11111111userId : " + userId);
        List<Member> foundFriend = mypageService.searchFriend(userId);
//        List<String>list = new ArrayList<String>();
//        for(int i=0;i<foundFriend.size();i++){
//            list.add(foundFriend.get(i));
//        }
//        String listStr = list.toString();
//        System.out.println("222222List : " + listStr);
        return foundFriend;
    }



    /**
     * 친구 추가하기
     */


    @PostMapping(value="/makeFriend.do")
    public String makeFriend(Friend friend){
        Friend myfriend = mypageService.makeFriend(friend);

        return "redirect:/mypage/friendList.do";

    }

}
