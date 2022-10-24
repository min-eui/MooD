package mood.moodmyapp.domain;

import lombok.*;
import mood.moodmyapp.jpaEntity.BaseTimeEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicUpdate  //변경된 칼럼만 업데이트해준다
@AllArgsConstructor // 생성자 추가
@NoArgsConstructor  //  기본생성자 자동 추가
@Builder
@Getter
@Setter
@Entity(name="Mood")// 회원가입에 관한 entity
@Table(name="tbl_mood")   //class이름과 테이블이름이 다를 경우에 써주어야함.
public class Mood extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //주키 자동생성 전략
    @Column(name="moodNum", length = 500, nullable = false, unique = true)
    private Long moodNum;               //감정인덱스번호

    @Column(name="userId", length = 50, nullable = false)
    private String userId;              //회원아이디(작성자)

    @Column(name="userProfile", length = 200, nullable = true)
    private String userProfile;   //프로필사진

    @Column(name="emotion", length = 20, nullable = true)
    private String emotion;                //감정

    @Column(name="contents", length = 5000, nullable = false)
    private String contents;            //글내용

    @Column(name="photo", length = 800, nullable = true)
    private String photo;
    //이미지
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="reg_date", length = 50, nullable = false)
    private LocalDateTime reg_date; //작성일

    @Column(name="viewers", length = 100, nullable = true)
    private String viewers;             //본사람

    @Column(name="likers", length = 100, nullable = true)
    private String likers;              //좋아요한사람

}
