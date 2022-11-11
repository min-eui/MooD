package mood.moodmyapp.domain;

import lombok.*;
import mood.moodmyapp.jpaEntity.BaseTimeEntity;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Collections.emptySet;

//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 전략
//@DiscriminatorColumn(name="moodNum")   //  구분하는 칼럼
@DynamicUpdate  //변경된 칼럼만 업데이트해준다
@AllArgsConstructor // 생성자 추가
@NoArgsConstructor  //  기본생성자 자동 추가
@Builder
@Getter
@Setter
@Entity(name="Mood")
@Table(name="tbl_mood")   //class 이름과 테이블 이름이 다를 경우에 써주어야함.
public class Mood extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //주키 자동생성 전략
    @Column(name="moodNum", length = 500, nullable = false, unique = true)
    private Long moodNum;               //감정인덱스번호


//    @BatchSize(size = 100)
    @OneToMany(mappedBy = "moodNum", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<IsLike> isLike = emptySet();

    @Column(name="userId", length = 50, nullable = false)
    private String userId;              //회원아이디(작성자)

    @Column(name="userProfile", length = 200, nullable = true)
    private String userProfile;   //프로필사진

    @Column(name="emotion", length = 20, nullable = true)
    private String emotion;                //감정

    @Column(name="contents", length = 5000, nullable = false)
    private String contents;            //글내용

    //이미지
    @ElementCollection
    @CollectionTable(name="tbl_uploadFile", joinColumns = @JoinColumn(name="moodNum"))
    private List<UploadFile> imageFiles;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="reg_date", length = 50, nullable = false)
    private LocalDateTime reg_date; //작성일


    @Column(name="totalLike", length = 500, nullable = true)
    private Long totalLike; //좋아요 개수

    @Column(name="likeUserList", length = 200, nullable = true)
    private String likeUserList;    //좋아요한 사람들


}
