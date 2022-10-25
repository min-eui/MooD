//package mood.moodmyapp.repository;
//
//import mood.moodmyapp.domain.Mood;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//
//@Repository
//public class MoodRepository2 extends MoodJpaRepository{
//
//
//        Mood save(Mood mood);
//
//        @Query("SELECT m FROM Mood  m ORDER BY m.reg_date DESC")
//        List<Mood> findAllOrderByReg_dateDesc();
//
//        @Query("SELECT m FROM Mood  m WHERE m.moodNum =:moodNum")
//        Mood findByMoodNum(@Param("moodNum") Long moodNum);
//
//        @Modifying
//        @Transactional
//        @Query("DELETE FROM Mood m WHERE m.moodNum =:moodNum")
//        int deleteByMoodNum(Long moodNum);
//
//
//}
