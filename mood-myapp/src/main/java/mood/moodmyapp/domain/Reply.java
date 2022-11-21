package mood.moodmyapp.domain;

import lombok.*;
import mood.moodmyapp.jpaEntity.BaseTimeEntity;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor // 생성자 추가
@NoArgsConstructor  //  기본생성자 자동 추가
@Builder
@Getter
@Setter
@Entity(name="Reply")
@Table(name="tbl_reply")   //class 이름과 테이블 이름이 다를 경우에 써주어야함.
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="replyNum", length = 20, nullable = false, unique = true)
    private Long replyNum;                    //댓글인덱스번호

    @Column(name="moodNum", length = 500, nullable = false, unique = false)
    private Long moodNum;                    //감정인덱스번호

    @Column(name="reply_contents", length = 5000, nullable = false, unique = false)
    private String reply_contents;          //댓글내용

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="reg_date", length = 50, nullable = false, unique = false)
    private LocalDateTime reg_date;   //댓글등록일

    @Column(name="registrant", length = 20, nullable = false, unique = false)
    private String registrant;              //댓글작성
}
