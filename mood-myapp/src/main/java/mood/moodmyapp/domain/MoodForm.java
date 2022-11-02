package mood.moodmyapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
@Embeddable
@Data
@AllArgsConstructor //전체 필드에 대한 생성자
@NoArgsConstructor  //기본생성자
@Builder
//@Entity(name="MoodForm")//
//@Table(name="tbl_moodForm")   //class이름과 테이블이름이 다를 경우에 써주어야함.
//사진 저장용 폼
public class MoodForm {

        private Long moodNum;               //감정인덱스번호
        private String userId;              //회원아이디(작성자)
        private String userProfile;         //프로필사진
        private String emotion;             //감정
        private String contents;            //글내용
        private List<MultipartFile> imageFiles;               //이미지
        private LocalDateTime reg_date;     //작성일
        private String likers;              //좋아요한사람

    }


