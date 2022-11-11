package mood.moodmyapp.dto;

import lombok.*;
import mood.moodmyapp.domain.IsLike;
import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.domain.UploadFile;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MoodJoinDto {

//    Mood getMood();
//    IsLike getIsLike();

    Long getMoodNum();               //감정인덱스번호
    String getUserId();              //회원아이디(작성자)
    String getUserProfile();   //프로필사진
    String getEmotion();                //감정
    String getContents();            //글내용
    List<UploadFile> getImageFiles();   //이미지
    LocalDateTime getReg_date(); //작성일
    Long getTotalLike(); //좋아요 개수
    String getLikeUserList();    //좋아요한 사람들
    String getLikeUserId();  // 좋아요한 사람 아이디
    int getIsLike();       // 좋아요


}
