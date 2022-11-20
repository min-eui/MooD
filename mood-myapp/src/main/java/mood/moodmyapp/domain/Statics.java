package mood.moodmyapp.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicUpdate  //변경된 칼럼만 업데이트해준다
@AllArgsConstructor // 생성자 추가
@NoArgsConstructor  //  기본생성자 자동 추가
@Builder
@Getter
@Setter
@Entity(name="Statics")
@Table(name="tbl_statics")   //class 이름과 테이블 이름이 다를 경우에 써주어야함.
public class Statics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //주키 자동생성 전략
    @Column(name="staticsNum", length = 200, nullable = false, unique = true)
    private Long staticsNum;    // 통계인덱스번호

    @Column(name="moodNum", length = 500, nullable = false, unique = true)
    private Long moodNum;    // FK 글번호

    @Column(name="userId", length = 50, nullable = false)
    private String userId;  // 회원아이디

    @Column(name="emotion", length = 20, nullable = true)
    private String emotion; // 감정

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="static_reg_date", length = 50, nullable = false)
    private LocalDateTime static_reg_date;  // 등록일

    @Column(name="weekly_statics", length = 500, nullable = true)
    private String weekly_statics;  // 주간통계

    @Column(name="monthly_statics", length = 500, nullable = true)
    private String monthly_statics; // 월간통계


}
