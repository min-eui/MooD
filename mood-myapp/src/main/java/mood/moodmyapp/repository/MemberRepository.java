package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Friend;
import mood.moodmyapp.domain.Member;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends MemberJpaRepository {


    Member save(Member member);

    Optional<Member> findById(String id);

    @Query("SELECT m FROM Member  m WHERE m.userName = :userName")
    Optional<Member> findByName(@Param("userName") String userName);

    @Query("SELECT m FROM Member  m WHERE m.userId = :userId AND m.userPw = :userPw")
    Optional<Member> findByIdAndPw(String userId, @Param("userPw")String userPw);

    List<Member> findAll();

    @Query("SELECT m FROM Member  m WHERE m.userId = :userId")
    Optional<String> existByMemberId(String userId);

    @Query("SELECT m FROM Member m WHERE m.nickName = :nickName")
    Optional<String> existByMemberNickName(String nickName);

    @Query("SELECT m FROM Member  m WHERE m.phoneNum = :phoneNum")
    Optional<Member> findByPhoneNum(String phoneNum);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET  m.userPw = :updatePw WHERE m.userId = :userId")
    Optional<Integer> updatePw(String updatePw, String userId);

    @Query("SELECT m FROM Member  m WHERE m.userId = :userId")
    Optional<Boolean> findByKakaoId(String userId);

    //Mypage 친구 아이디 찾기

    //List<Member> findFriendByFriendId(String userId); //친구아이디를 맴버테이블에서 검색하기
    @Query("SELECT m FROM Member  m WHERE m.userId = :userId")
    Member findFriendByUserId(String userId); //친구아이디를 맴버테이블에서 검색하기


}