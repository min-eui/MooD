package mood.moodmyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mood.moodmyapp.domain.Member;

import java.util.Date;

@Data
@AllArgsConstructor //전체 필드에 대한 생성자
@NoArgsConstructor  //기본생성자
@Builder
public class MemberDto {

    private Long userNum;         // ID값
    private String userId;        // PK값
    private String userPw;        //  회원비밀번호
    private String userName;      //  회원이름
    private String nickName;      //  회원닉네임
    private String phoneNum;      //  회원휴대폰번호
    private String kakaoYn;       //  카카오로그인여부
    private Date reg_date; // 날짜와 시간

    private String term1;      //  약관1동의여부

    private String term2;      //  약관2동의여부

    /* DTO > Entity */
/*    public Member toEntity(){
        Member member = Member.builder()
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .nickName(nickName)
                .phoneNum(phoneNum)
                .kakaoYn(kakaoYn)
                .term1(term1)
                .term2(term2)
                .build();
        return member;
    }*/

}
