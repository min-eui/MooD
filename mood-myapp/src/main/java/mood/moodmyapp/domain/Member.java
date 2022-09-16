package mood.moodmyapp.domain;

import lombok.*;
import mood.moodmyapp.jpaEntity.BaseTimeEntity;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor // 생성자 추가
@NoArgsConstructor  //  기본생성자 자동 추가
@Builder
@Getter
@Setter
@Entity(name="Member") // 회원가입에 관한 entity
@Table(name="tbl_member")   //class이름과 테이블이름이 다를 경우에 써주어야함.
public class Member extends BaseTimeEntity {

            @Id
            @Column(name="userId", length = 50, nullable = false, unique = true)
            private String userId;  // PK값

            @Column(name="userPw", length = 500, nullable = false)
            private String userPw;      //  회원비밀번호

            @Column(name="userName", length = 20, nullable = false)
            private String userName;      //  회원이름

            @Column(name="nickName", length = 20, nullable = false, unique = true)
            private String nickName;      //  회원닉네임

            @Column(name="phoneNum", length = 20, nullable = false, unique = true)
            private String phoneNum;      //  회원휴대폰번호

            @Column(name="kakaoYn", length = 1, nullable = true)
            private String kakaoYn = "N";      //  카카오로그인여부

            @Column(name="term1", length = 1, nullable = false)
            private String term1 = "N";      //  약관1동의여부

            @Column(name="term2", length = 1, nullable = false)
            private String term2 = "N";      //  약관1동의여부

            @CreatedDate
            @Column(name="reg_date", length = 50, nullable = false)
            private LocalDateTime reg_date;

}
