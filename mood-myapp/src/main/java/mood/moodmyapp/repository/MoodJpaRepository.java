package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Mood;

import java.time.LocalDateTime;

public interface MoodJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<Mood, Long> {

//   Long CountByEmotion(LocalDateTime yearTodate);
}
