package mood.moodmyapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mood.moodmyapp.common.EncryptionUtils;
import mood.moodmyapp.common.KakaoOauthService;
import mood.moodmyapp.common.UUIDGeneraterUtils;
import mood.moodmyapp.domain.Member;
import mood.moodmyapp.repository.JpaMemberRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MemberService {

    //회원 서비스가 있으려면 회원 리포지토리가 있어야함
    private  final JpaMemberRepository memberRepository;
    private final KakaoOauthService kakaoOauthService;
    private final ObjectMapper objectMapper;


    //내부에서 직접 new해서 바꿔주는게 아니라 외부에서 넣어주게끔 생성자를 만들어줌
    @Autowired
    public MemberService(JpaMemberRepository memberRepository, KakaoOauthService kakaoOauthService, ObjectMapper objectMapper) {

        this.memberRepository = memberRepository;
        this.kakaoOauthService = kakaoOauthService;
        this.objectMapper = objectMapper;
    }
    /**
     * 회원 가입
     */
    @Transactional
    public String join(Member member){
        //같은 이름이 있는 중복 회원x
        validateDuplicateMember(member);
       member = Member.builder()
                       .userId(member.getUserId())
                       .userPw(EncryptionUtils.encryptSHA256(member.getUserPw()))
                       .userName(member.getUserName())
                       .nickName(member.getNickName())
                       .phoneNum(member.getPhoneNum())
                       .kakaoYn(member.getKakaoYn())
                       .term1(member.getTerm1())
                       .term2(member.getTerm2())
                       .build();
        System.out.println(EncryptionUtils.encryptSHA256(member.getUserPw()));
        memberRepository.save(member);  //리포지토리에 맴버만 save하면 됨
        return "Success";
    }
    /**
     * 아이디 중복체크
     */
    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getUserId())
                .ifPresent(m -> {
                    throw  new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    /**
     * 아이디 중복체크
     */
    public boolean existByMemberId(String userId) {

        Optional<String> valId = memberRepository.existByMemberId(userId);

        if(valId
                .isPresent()) {
            return true;
        }else{
            return false;
        }

    }

    /**
     * 닉네임 중복체크
     */
    public boolean existByMemberNickName(String nickName) {

        Optional<String> valNickName = memberRepository.existByMemberNickName(nickName);

        if(valNickName
                .isPresent()) {
            return true;
        }
        return false;
    }

    /**
     * 전체 회원 조회
     */

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 아이디로 회원 조회
     */

    public Optional<Member> findById(String userId){
        return memberRepository.findById(userId);
    }

    /**
     * 카카오로 회원가입
     */
    public Member kakaoJoin(String userInfo){
        Member member = new Member();
        // 코드를 이용하여 accessToken 추출
       // String access_token = kakaoOauthService.getKakaoAccessToken(code);
        // accessToken을 이용하여 사용자 정보 추출
        //String userInfo = kakaoOauthService.getKakaoAccessToken(access_token);
        //랜덤 휴대전화 번호 생성을 위한 UUID(11자리)
        System.out.println("##################randomSt : " + UUIDGeneraterUtils.makeShortUUID());
        // UUID는 생성 시 UUID 형태이므로 String 형태로 바꿔야함.

        try{
            JsonNode jsonNode = objectMapper.readTree(userInfo);
            String userIdRaw = String.valueOf(jsonNode.get("kakao_account").get("email"));
            String userId= userIdRaw.replace("\"","");
            String userNameRaw = String.valueOf(jsonNode.get("kakao_account").get("profile").get("nickname"));
            String userName = userNameRaw.replace("\"","");
            String nickName = userName;
            String userPw = EncryptionUtils.encryptSHA256(UUID.randomUUID().toString());
            String phoneNum = UUIDGeneraterUtils.makeShortUUID();    // 입력받기 kakaoYn Y인사람은 카카오계정으로 로그인하도록 유도
            String kakaoYn = "Y";
            String term1 = "Y"; //입력받기
            String term2 = "Y"; //입력받기
            LocalDateTime reg_date = LocalDateTime.now();

            member = Member.builder()
                    .userId(userId)
                    .userPw(userPw)
                    .userName(userName)
                    .nickName(nickName)
                    .phoneNum(phoneNum)
                    .kakaoYn(kakaoYn)
                    .term1(term1)
                    .term2(term2)
                    .reg_date(reg_date)
                    .build();
            memberRepository.save(member);
            System.out.println("11111아이디 : " + member.getUserId());
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("userId : " + member.getUserId()+ " userPw : " + member.getUserPw()+ " userName : " +member.getUserName()+ " nickName : " +member.getNickName()+ " phoneNum : " +member.getPhoneNum()+ " kakakoYn : " +member.getKakaoYn()+ " term1 : " +member.getTerm1()+ " term2 : " +member.getTerm2());
        return member;

    }
    /**
     * 카카오 아이디 중복체크
     */
    public Optional<Boolean> findByKakaoId(String userId) {
        Optional<Boolean> findKaKaoUser = memberRepository.findByKakaoId(userId);

        return findKaKaoUser;
    }


}
