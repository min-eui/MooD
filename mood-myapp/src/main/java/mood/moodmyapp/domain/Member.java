package mood.moodmyapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import mood.moodmyapp.jpaEntity.BaseTimeEntity;
import mood.moodmyapp.jpaEntity.BaseTimeModifyEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DynamicUpdate  //변경된 칼럼만 업데이트해준다
@AllArgsConstructor // 생성자 추가
@NoArgsConstructor  //  기본생성자 자동 추가
@Builder
@Getter
@Setter
@Entity(name="Member")// 회원가입에 관한 entity
@Table(name="tbl_member")   //class이름과 테이블이름이 다를 경우에 써주어야함.
public class Member extends BaseTimeModifyEntity {

            @Id
            //@GeneratedValue //주키 자동생성 전략
            @Column(name="userId", length = 50, nullable = false, unique = true)
            private String userId;  // PK값

            @Column(name="userPw", length = 500, nullable = false)
            private String userPw;      //  회원비밀번호

            @Column(name="userName", length = 20, nullable = false)
            private String userName;      //  회원이름

            @Column(name="nickName", length = 20, nullable = false, unique = true)
            private String nickName;      //  회원닉네임

            @Column(name="userProfile", length = 200, nullable = true)
            private String userProfile;   //프로필사진

            @Column(name="phoneNum", length = 20, nullable = false, unique = true)
            private String phoneNum;      //  회원휴대폰번호

            @Column(name="kakaoYn", length = 1, nullable = true)
            private String kakaoYn = "N";      //  카카오로그인여부

            @Column(name="term1", length = 1, nullable = false)
            private String term1 = "N";      //  약관1동의여부

            @Column(name="term2", length = 1, nullable = false)
            private String term2 = "N";      //  약관1동의여부

            @CreatedDate
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Column(name="reg_date", length = 50, nullable = false)
            private LocalDateTime reg_date;

            @LastModifiedDate
            @Column(name="update_date", length = 50, nullable = true)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime update_date;

}
