package mood.moodmyapp.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@DynamicUpdate  //변경된 칼럼만 업데이트해준다
@NoArgsConstructor  //  기본생성자 자동 추가
@Builder
@Getter
@Setter
@Entity(name="IsLike")
@Table(name="tbl_isLike")   //class이름과 테이블이름이 다를 경우에 써주어야함.
public class IsLike{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="id", length = 20, nullable = false, unique = true)
//    private Long id;        //id

    @Id
    @Column(name="moodNum", nullable = false, unique = true)
//    @JoinColumn(name="moodNum", nullable = false, unique = true)
    private Long moodNum;   // 글번호

    @Column(name="userId", length = 50, nullable = false)
    private String userId;  // 좋아요한 사람 아이디
    @Column(name="isLike", length = 5, nullable = false)
    private int isLike;       // 좋아요

    public IsLike(Long moodNum, String userId, int isLike) {
        this.moodNum = moodNum;
        this.userId = userId;
        this.isLike = isLike;
    }

}
