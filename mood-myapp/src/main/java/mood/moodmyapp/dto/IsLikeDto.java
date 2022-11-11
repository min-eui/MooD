package mood.moodmyapp.dto;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
@Data
@AllArgsConstructor //전체 필드에 대한 생성자
@NoArgsConstructor  //기본생성자
@Builder
public class IsLikeDto {

    private Long isLikeId;       //아이디
    private Long moodNum;   // 글번호
    private String likeUserId;  // 좋아요한 사람 아이디
    private int isLike;       // 좋아요
}
