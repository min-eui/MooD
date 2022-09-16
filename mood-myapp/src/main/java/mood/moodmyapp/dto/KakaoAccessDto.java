package mood.moodmyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor //전체 필드에 대한 생성자
@NoArgsConstructor  //기본생성자
@Builder
public class KakaoAccessDto {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private String id_token;
    private String expires_in;
    private String scope;
    private String refresh_token_expires_in;


}
