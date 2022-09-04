package mood.moodmyapp.persistence;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.assertj.core.api.Fail.fail;

public class JDBCTest {
    @Test
    public void testConnection() {

        try(Connection con =
                    DriverManager.getConnection(
                            "jdbc:mariadb://localhost:3306/mood?characterEncoding=UTF-8&serverTimezone=Asia/Seoul",
                            "root",
                            "root")){
            System.out.println(con);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}
