package mood.moodmyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor //전체 필드에 대한 생성자
@NoArgsConstructor  //기본생성자
@Builder
public class MoodDto {

    private Long moodNum;               //감정인덱스번호
    private String userId;              //회원아이디(작성자)
    private String userProfile;         //프로필사진
    private String emotion;                //감정
    private String contents;            //글내용
    private String photo;               //이미지
    private LocalDateTime reg_date;     //작성일
    private String viewers;             //본사람
    private String likers;              //좋아요한사람

}
