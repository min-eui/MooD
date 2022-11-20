package mood.moodmyapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
@Repository
public interface StaticsRepository extends StaticsJpaRepository {

//    @Query("SELECT s FROM Statics s WHERE s.")
//    HashMap<String, Object> findByStatics_reg_date(String searchYear);
}
