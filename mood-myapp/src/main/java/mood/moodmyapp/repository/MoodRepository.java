package mood.moodmyapp.repository;

import mood.moodmyapp.domain.IsLike;
import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.dto.MoodDto;
import mood.moodmyapp.dto.MoodJoinDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface MoodRepository extends MoodJpaRepository {


    Mood save(Mood mood);

    // 지연로딩에서 즉시로딩으로 바꾸고 싶은 필드의 이름
    @EntityGraph(attributePaths = {"imageFiles"})
    @Query("SELECT distinct m FROM Mood  m left  join fetch m.isLike ORDER BY m.reg_date DESC")
    List<Mood> findAllOrderByReg_dateDesc();

//    @Query("SELECT distinct m FROM Mood  m left  join fetch m.isLike left  join fetch m.imageFiles ORDER BY m.reg_date DESC")
//    List<Mood> findAllOrderByReg_dateDesc();


    @Query("SELECT m FROM Mood  m WHERE m.moodNum =:moodNum")
    Mood findByMoodNum(@Param("moodNum") Long moodNum);

    @Modifying
    @Transactional
    @Query("DELETE FROM Mood m WHERE m.moodNum =:moodNum")
    int deleteByMoodNum(Long moodNum);

    /**
     * tbl_mood 좋아요 개수
     */
    @Modifying
    @Transactional
    @Query("UPDATE Mood m SET m.totalLike =m.totalLike+1 WHERE m.moodNum =:moodNum")
    void addTotalLike(Long moodNum);

    /**
     * tbl_mood 좋아요 처리
     */
    @Modifying
    @Transactional
    @Query("UPDATE Mood m SET m.totalLike =m.totalLike-1 WHERE m.moodNum =:moodNum")
    void subtractTotalLike(Long moodNum);


//    @Modifying
//    @Transactional
//    @Query("UPDATE SET FROM Mood m WHERE m.moodNum =:moodNum")
//    int deleteByMoodNum(Long moodNum);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Mood m SET m.isLike=:isLike WHERE m.moodNum =:moodNum")
//    void updateIsLike(@Param("moodNum")Long moodNum, @Param("isLike") int isLike);

}
