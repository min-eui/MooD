package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaMemberRepository extends JpaRepository<Member, String>, MemberRepository {
//  비워있어도 잘 작동함
//  JpaRepository 인터페이스를 상속받을 때 엔티티 클래스의 타입(Member)과 PK에 해당하는 데이터 타입(String) 선언하면
    //해당 엔티티 클래스와 매핑되는 테이블의 CRUD기능을 사용할 수 있다.
    @Override
    Member save(Member member);

    @Override
    Optional<Member> findById(String id);

    @Override
    @Query("SELECT m FROM Member  m WHERE m.userName = :userName")
    Optional<Member> findByName(@Param("userName") String userName);

    @Query("SELECT m FROM Member  m WHERE m.userId = :userId AND m.userPw = :userPw")
    @Override
    Optional<Member> findByIdAndPw(String userId, @Param("userPw")String userPw);

    @Override
    List<Member> findAll();

    @Query("SELECT m FROM Member  m WHERE m.userId = :userId")
    @Override
    Optional<String> existByMemberId(String userId);
}
