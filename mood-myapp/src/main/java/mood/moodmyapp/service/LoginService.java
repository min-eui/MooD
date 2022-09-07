package mood.moodmyapp.service;

import mood.moodmyapp.common.EncryptionUtils;
import mood.moodmyapp.domain.Member;
import mood.moodmyapp.repository.MemberRepository;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Transactional
@Service
public class LoginService {

    @Autowired
    private final MemberRepository memberRepository;

    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean login(Member member) {

        Optional<Member> findIdAndPw = memberRepository.findByIdAndPw(member.getUserId(), member.getUserPw());

        if (findIdAndPw.isPresent()) {
            System.out.println("로그인 성공");
            return true;
        }
        System.out.println("로그인 실패");
        return false;
    }

    public String findIdByPhoneNum(String phoneNum) {

        Optional<Member> findPhoneNum = memberRepository.findByPhoneNum(phoneNum);

        if(findPhoneNum.isPresent()){
            System.out.println("해당 번호로 가입된 아이디가 있습니다.");
            Member userId = findPhoneNum.get(); //해당번호로 가입된 아이디 리턴
            String userIdStr =  userId.getUserId(); //Member타입에서 값 가져와서 .get으로 값 가져오고 String으로 넣어줌
            System.out.println(userIdStr);
            return userIdStr;
        }
        System.out.println("해당 번호로 가입된 아이디가 없습니다.");
        return "";
    }

    public void certifiedPhoneNum(String phoneNum, String numStr) {
        String api_key = "NCSLWWB6T1QJNRGF";
        String api_secret = "GEVJDNFQFDO78XWRUBV2BGJIHRYIMPDX";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNum);    // 수신전화번호
        params.put("from", "01041422557");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "[MooD] 휴대폰인증 메시지 : 인증번호는" + "["+numStr+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }


    public boolean updatePw(String updatePw, String userId){
        //memberRepository.updatePw(updatePw,userId);
        // 새로운 비밀번호 암호화
        String encryptPw = EncryptionUtils.encryptSHA256(updatePw);
        Optional<Integer> isUpdatePw = memberRepository.updatePw(encryptPw,userId);

        if(isUpdatePw.isPresent()){
            return true;
        }
        return false;
    }

}
