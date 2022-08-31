package mood.moodmyapp;

import mood.moodmyapp.domain.Member;
import mood.moodmyapp.repository.JpaMemberRepository;
import mood.moodmyapp.service.MemberService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
@SpringBootTest
public class JpaMemberTest {

    @Autowired
    MemberService memberService;
    @Autowired
    JpaMemberRepository memberRepository;

    @Test
    //회원가입
    public void  save(){

        Date date  = new Date();

        Member member = new Member();
        //1. 회원 파라미터 생성
       /* member = Member.builder()
                .userId("fnqlehcl11")
                .userPw("1234")
                .userName("김주현")
                .nickName("주주")
                .phoneNum("01041422557")
                .kakaoYn("N")
                .term1("Y")
                .term2("Y")
                .build();*/
        //given 어떤걸 가지고
        member.setUserId("juju1");
        member.setUserPw("123");
        member.setUserName("김이름");
        member.setNickName("김주");
        member.setPhoneNum("01066667777");
        member.setKakaoYn("N");
        member.setTerm1("Y");
        member.setTerm2("Y");


        //2. 회원 저장
        //when 뭔가를 실행했을때
        String saveId = memberService.join(member);

        Member findMember = memberService.findById(saveId).get();
        assertThat(member.getUserId()).isEqualTo(findMember.getUserId());

        //memberRepository.save(member);

       /* //3. 회원조회
        Member member1 = memberRepository.findById((Long)"fnqlehcl11").get();
        assertThat(member1.getNickName()).isEqualTo("주주");*/

    }

}
