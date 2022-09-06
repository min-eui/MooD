package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(String id);

    Optional<Member> findByName(String userName);

    List<Member> findAll();

    Optional<Member> findByIdAndPw(String userId, String userPw);

    Optional<String> existByMemberId(String userId); // 아이디 중복 검사

    Optional<String> existByMemberNickName(String nickName); // 닉네임 중복 검사

  //  Optional<String> findByPhoneNum(String phoneNum); // 휴대폰번호로 아이디 찾기
    Optional<Member> findByPhoneNum(String phoneNum); // 휴대폰번호로 아이디 찾기

}