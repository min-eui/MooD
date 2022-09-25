package mood.moodmyapp.domain;

import lombok.*;
import mood.moodmyapp.jpaEntity.BaseTimeEntity;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor // 생성자 추가
@NoArgsConstructor  //  기본생성자 자동 추가
@Builder
@Getter
@Setter
@Entity(name="Friend")
@Table(name="tbl_friend")
public class Friend extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="friendNum", length = 50, nullable = false, unique = true)
    private Long friendNum; // 친구 인덱스 번호

    @Column(name="userId", length = 50, nullable = false, unique = false)
    private String userId;  //유저 아이디

    @Column(name="friendId", length = 50, nullable = false, unique = false)
    private String friendId;    // 친구 아이디

    @Column(name="isEqualMem", length = 1, nullable = true, unique = false)
    private String isEqualMem;  // 서비스회원 구분값

    @CreatedDate
    @Column(name="reg_date", length = 50, nullable = false)
    private LocalDateTime reg_date;

}
