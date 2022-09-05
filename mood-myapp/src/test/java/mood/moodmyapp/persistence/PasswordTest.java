package mood.moodmyapp.persistence;


import mood.moodmyapp.common.EncryptionUtils;
import mood.moodmyapp.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PasswordTest {


    @Test
    public void isPasswordEqual(){
        //given
        String pw = "123";

        //when
        String encryptPw = EncryptionUtils.encryptSHA256(pw);

        //then
        assertAll(
                // 평문 비밀번호와 암호화 비밀번호가 서로다른지
                ()->assertNotEquals(pw,encryptPw),
                // 암호된 비밀번호가 일치하는지
                ()-> assertTrue(EncryptionUtils.encryptSHA256(pw).matches(encryptPw))
        );


    }




}
