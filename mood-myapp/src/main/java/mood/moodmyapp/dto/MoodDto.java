package mood.moodmyapp.dto;

import lombok.*;
import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.domain.UploadFile;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor //전체 필드에 대한 생성자
@NoArgsConstructor  //기본생성자
@Builder

public class MoodDto {

    private Long moodNum;               //감정인덱스번호
    private String userId;              //회원아이디(작성자)
    private String userProfile;         //프로필사진
    private String emotion;             //감정
    private String contents;            //글내용
    private List<UploadFile> imageFiles;               //이미지
    private LocalDateTime reg_date;     //작성일
    private Long totalLike;             //좋아요 개수
    private String likeUserList;        //좋아요한 사람들
    private Long isLikeId;       //아이디
    private String likeUserId;  // 좋아요한 사람 아이디
    private int isLike;       // 좋아요


}
