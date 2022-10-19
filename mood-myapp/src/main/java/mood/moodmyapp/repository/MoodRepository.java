package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Mood;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodRepository extends MoodJpaRepository {

    Mood save(Mood member);

}
