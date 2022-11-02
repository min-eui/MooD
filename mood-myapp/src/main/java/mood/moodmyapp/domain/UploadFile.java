package mood.moodmyapp.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


//@DynamicUpdate  //변경된 칼럼만 업데이트해준다
@NoArgsConstructor  //  기본생성자 자동 추가
@Builder
@Getter
@Setter
@Embeddable //임베디드 타입
public class UploadFile {


    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

}
