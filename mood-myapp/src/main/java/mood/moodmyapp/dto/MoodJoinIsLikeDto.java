package mood.moodmyapp.dto;

import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.domain.UploadFile;

import java.time.LocalDateTime;
import java.util.List;

public class MoodJoinIsLikeDto {

    private Long moodNum;               //감정인덱스번호
    private String userId;              //회원아이디(작성자)
    private String userProfile;   //프로필사진
    private String emotion;                //감정
    private String contents;            //글내용
    private List<UploadFile> imageFile;   //이미지
    private LocalDateTime reg_date; //작성일
    private Long totalLike; //좋아요 개수
    private String likeUserList;    //좋아요한 사람들
    private String likeUserId;  // 좋아요한 사람 아이디
    private int isLike;       // 좋아요

    public MoodJoinIsLikeDto(Long moodNum, String userId, String userProfile, String emotion, String contents, List<UploadFile> imageFile, LocalDateTime reg_date, Long totalLike, String likeUserList, String likeUserId, int isLike) {
        this.moodNum = moodNum;
        this.userId = userId;
        this.userProfile = userProfile;
        this.emotion = emotion;
        this.contents = contents;
        this.imageFile = imageFile;
        this.reg_date = reg_date;
        this.totalLike = totalLike;
        this.likeUserList = likeUserList;
        this.likeUserId = likeUserId;
        this.isLike = isLike;
    }

    public MoodJoinIsLikeDto() {
    }

    public MoodJoinIsLikeDto(List<Mood> moodList) {
    }
}
