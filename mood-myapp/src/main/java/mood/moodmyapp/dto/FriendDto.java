package mood.moodmyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor //전체 필드에 대한 생성자
@NoArgsConstructor  //기본생성자
@Builder
public class FriendDto {

    private Long friendNum; // 친구 인덱스 번호
    private String userId;  //유저 아이디
    private String friendId;    // 친구 아이디
    private String isEqualMem;  // 서비스회원 구분값
    private LocalDateTime reg_date;
}
