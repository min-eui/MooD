package mood.moodmyapp.service;

import mood.moodmyapp.domain.Member;
import mood.moodmyapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
