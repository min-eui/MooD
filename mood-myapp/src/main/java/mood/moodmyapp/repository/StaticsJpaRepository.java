package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Statics;
import org.springframework.stereotype.Repository;

@Repository
public interface StaticsJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<Statics, Long>{
}
