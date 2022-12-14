package mood.moodmyapp.controller;


import mood.moodmyapp.Session.SessionConstant;
import mood.moodmyapp.common.EncryptionUtils;
import mood.moodmyapp.domain.Friend;
import mood.moodmyapp.domain.Member;
import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.service.MemberService;
import mood.moodmyapp.service.MoodService;
import mood.moodmyapp.service.MypageService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RequestMapping(value="/mypage")
@Controller
public class MypageController {




    private final MypageService mypageService;
    private final MemberService memberService;
    private final MoodService moodService;

    @Autowired
    public MypageController(MypageService mypageService, MemberService memberService, MoodService moodService) {
        this.memberService = memberService;
        this.mypageService = mypageService;
        this.moodService = moodService;
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

        //내가 쓴 글만 조회하기
        List<Mood> myPostList = moodService.findMyPosting(isMember);
        model.addAttribute("myPostList",myPostList);
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
    public Map<String,String> searchFriend(String friendId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        friendId = request.getParameter("userId");

        HttpSession session = request.getSession(true);
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);

        System.out.println("Controller 친구찾기 friendId : " + friendId);
        System.out.println("Controller 내아이디 userId : " + userId);

        Optional isFriend = mypageService.existFriend(userId,friendId);

        // 자기자신을 친구로 조회할 경우
        if(friendId.equals(userId)){

            HashMap<String,String> errorCode =  new HashMap<String,String>();
            String code = "자기 자신은 친구 추가할 수 없습니다";
            errorCode.put("code",code);
            return errorCode;

        }else if(isFriend.isPresent()){
            HashMap<String,String> errorCode =  new HashMap<String,String>();
            String code = "이미 추가된 친구입니다.";
            errorCode.put("code",code);
            return errorCode;
        }else{
            HashMap<String,String> foundFriend =  new HashMap<String,String>();
            String friend = mypageService.searchFriend(friendId);
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

        HttpSession session = request.getSession(true);
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);
        mypageService.makeFriend(userId, friendId);

        friendMap.put("friendId",friendId);
        return friendMap;

    }

    /**
     * 친구 삭제하기
     */
    @ResponseBody
    @PostMapping(value="/deleteFriend.do")
    public Map<String,String> deleteFriend(HttpServletRequest request, @RequestBody HashMap<String, String>delFriendMap){
        String delId = delFriendMap.get("delId");
        HttpSession session = request.getSession(true);
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);
        int delRes = mypageService.deleteFriend(userId,delId);
        HashMap<String,String> stateCode =  new HashMap<String,String>();

            if(delRes>0){
                stateCode.put("delId",delId);
            }else{
                String code = "친구삭제에 실패했습니다.";
                stateCode.put("code",code);
            }

        return stateCode;
    }


    /**
     * 내정보 수정 페이지
     */

    @GetMapping(value="/updateInfo.do")
    public String myInfoEdit(HttpServletRequest request,Model model) throws Exception {

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

        // 세션에 저장된 아이디로 회원조회/
        Optional<Member> memberInfo = memberService.findById(userId);
        Member member;
        if(memberInfo.isPresent()){
            member = memberInfo.get();
        }else{
            throw new Exception();
        }
        model.addAttribute("member",member);

        return "/mypage/updateInfo";
    }

    @PostMapping(value="/updateInfo.do")
    public String updateInfoProc(HttpServletRequest request, Member member){
        HttpSession session = request.getSession(false);
        String userId = (String)session.getAttribute(SessionConstant.LOGIN_MEMBER);
        member.setUserId(userId);   //세션에서 아이디 세팅
        String userPw = "";
        if(!member.getUserPw().equals("")){
            //수정할 값이 있을때 암호화 처리 해준다.
            userPw = EncryptionUtils.encryptSHA256(member.getUserPw());

        }else{
            //변경값이 없을때 원래 비밀번호로 세팅
            Optional<Member> exMemInfo = memberService.findById(userId);
            userPw = exMemInfo.get().getUserPw();
        }

        member.setUserPw(userPw);
        Member isUpdate = mypageService.updateInfoProc(member);

        return "redirect:/";
    }


}
