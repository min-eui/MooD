package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Mood;

public interface MoodJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<Mood, Long> {
}
