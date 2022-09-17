package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Member;


public interface MemberJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<Member, String>  {
//  비워있어도 잘 작동함
//  JpaRepository 인터페이스를 상속받을 때 엔티티 클래스의 타입(Member)과 PK에 해당하는 데이터 타입(String) 선언하면
    //해당 엔티티 클래스와 매핑되는 테이블의 CRUD기능을 사용할 수 있다.

}
