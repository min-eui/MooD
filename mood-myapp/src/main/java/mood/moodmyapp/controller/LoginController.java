package mood.moodmyapp.controller;

import mood.moodmyapp.common.EncryptionUtils;
import mood.moodmyapp.common.KakaoOauthService;
import mood.moodmyapp.domain.Member;
import mood.moodmyapp.service.LoginService;
import mood.moodmyapp.session.SessionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @Autowired
    private final KakaoOauthService kakaoOauthService;

    public LoginController(LoginService loginService, KakaoOauthService kakaoOauthService) {
        this.loginService = loginService;
        this.kakaoOauthService = kakaoOauthService;
    }

    /**
     *  로그인 페이지
     */

    @GetMapping("/login/login.do")
    public String loginForm(){
        return "/login/login";
    }

    /**
     * 로그인 처리
     * @param
     */
    @PostMapping("/login/login.do")
    public String loginProc(@ModelAttribute Member member, HttpServletRequest request){

        // 전달받은 pw를 암호화하여 비교
        member= Member.builder()
                .userId(member.getUserId())
                .userPw(EncryptionUtils.encryptSHA256(member.getUserPw())).build();

        // 로그인 성공
        if(loginService.login(member)){
            boolean loginMember = loginService.login(member);
            HttpSession session = request.getSession(); //세션이 있으면 세션 반환, 없으면 신규 세션을 생성하여 반환
            session.setAttribute(SessionConstant.LOGIN_MEMBER,loginMember); //이름, 값을 인자로 세션값 바인딩

            return "redirect:/";
        }
        return "/login/login";
    }

    /**
     * 로그아웃 처리
     * @param
     */
    @PostMapping("/logout.do")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();   //세션 날림
        }
        System.out.println("로그아웃 성공");
        return "redirect:/";
    }

    /**
     * 아이디/비밀번호찾기 페이지
     * @param
     */
    @GetMapping(value = "/login/findIdPw.do")
    public String findIdPw(){
        return "/login/findIdPw";
    }


    /**
     * (휴대폰번호로)아이디 찾기
     * @param
     */
    @ResponseBody
    @GetMapping(value="/login/findId.do")
    public Map findIdByPhoneNum(@RequestParam(value="phoneNum") String phoneNum, String numStr , Model model){
        String isExistId = loginService.findIdByPhoneNum(phoneNum);

        Map<String,String> map = new HashMap<String,String>();
        map.put("",""); // map 초기화
        numStr = "";

        if (isExistId != ""){
            Random rand  = new Random();
            for(int i=0; i<4; i++) {
                String ran = Integer.toString(rand.nextInt(10));
                numStr+=ran;
            }

            System.out.println("수신자 번호 : " + phoneNum);
            System.out.println("인증번호 : " + numStr);
            loginService.certifiedPhoneNum(phoneNum,numStr);

            map.put("numStr",numStr);
            map.put("userId",isExistId);
            return map;
        }

        return map;
    }


    /**
     * 비밀번호 변경
     * @param
     */

    @ResponseBody
    @PostMapping(value="/login/findPw.do", produces = "text/plain;charset=utf-8")
    public String findPw(String updatePw, String userId){

        boolean isUpdatePw = loginService.updatePw(updatePw, userId);

        System.out.println("updatePw : " + updatePw);
        System.out.println("userId : " + userId);
        if(isUpdatePw){
            return "redirect:/";
        }else{
            return "/login/findIdPw";
        }

    }


    /**
     * 카카오톡으로 로그인 callback
     * [GET] /login/kakaoOauth.do"
     */
    @GetMapping(value="/login/kakaoOauth.do")
    public String kakaoLogin(@RequestParam String code){
        System.out.println(code);   // 카카오 인가코드
       String access_Token = kakaoOauthService.getKakaoAccessToken(code);
        kakaoOauthService.createKakaoUser(access_Token);

        return "/login/kakaoOauth";
    }

}
