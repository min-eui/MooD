package mood.moodmyapp.service;

import mood.moodmyapp.domain.Member;
import mood.moodmyapp.repository.JpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    //회원 서비스가 있으려면 회원 리포지토리가 있어야함
    private  final JpaMemberRepository memberRepository;


    //내부에서 직접 new해서 바꿔주는게 아니라 외부에서 넣어주게끔 생성자를 만들어줌
    @Autowired
    public MemberService(JpaMemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }
    /**
     * 회원 가입
     */
    @Transactional
    public String join(Member member){
        //같은 이름이 있는 중복 회원x
        validateDuplicateMember(member);
        memberRepository.save(member);  //리포지토리에 맴버만 save하면 됨
        return "Success";
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getUserId())
                .ifPresent(m -> {
                    throw  new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public boolean existByMemberId(String userId) {

        Optional<String> valId = memberRepository.existByMemberId(userId);

        if(valId
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
     * 아이디 중복 체크
     */

/*    public Optional<Member> existByMemberId(String userId){
        return memberRepository.existByMemberId(userId);
    }*/

}
