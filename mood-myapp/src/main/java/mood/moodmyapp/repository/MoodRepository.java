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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface MoodRepository extends MoodJpaRepository {


    Mood save(Mood mood);

    /**
     * 글 전체조회하기
     * @return
     */
    // 지연로딩에서 즉시로딩으로 바꾸고 싶은 필드의 이름
    @EntityGraph(attributePaths = {"imageFiles"})
    @Query("SELECT distinct m FROM Mood  m left  join fetch m.isLike ORDER BY m.reg_date DESC")
    List<Mood> findAllOrderByReg_dateDesc();


    /**
     * 글 상세페이지
     * @param moodNum
     * @return
     */
      @EntityGraph(attributePaths = {"imageFiles"})
      @Query("SELECT m FROM Mood  m left  join fetch m.isLike WHERE m.moodNum =:moodNum")
      Mood findByMoodNum(@Param("moodNum") Long moodNum);


    /**
     * 글 삭제하기
     * @param moodNum
     * @return
     */
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

    /**
     * 월별 감정 개수
     */
            @Query(
    "SELECT YEAR(m.reg_date) as year, MONTH(m.reg_date) as month, " +
            "(SELECT count(m2) FROM Mood m2 WHERE m2.emotion='happy' AND YEAR(m2.reg_date) = YEAR(:reg_date) AND MONTH(m.reg_date)=MONTH(m2.reg_date) AND m2.userId = :userId) AS happy, " +
            "(SELECT count(m2) FROM Mood m2 WHERE m2.emotion='alright' AND YEAR(m2.reg_date) = YEAR(:reg_date) AND MONTH(m.reg_date)=MONTH(m2.reg_date) AND m2.userId = :userId) AS alright, " +
            "(SELECT count(m2) FROM Mood m2 WHERE m2.emotion='notBad' AND YEAR(m2.reg_date) = YEAR(:reg_date) AND MONTH(m.reg_date)=MONTH(m2.reg_date) AND m2.userId = :userId) AS notBad, " +
            "(SELECT count(m2) FROM Mood m2 WHERE m2.emotion='angry' AND YEAR(m2.reg_date) = YEAR(:reg_date)  AND MONTH(m.reg_date)=MONTH(m2.reg_date) AND m2.userId = :userId) AS angry, " +
            "(SELECT count(m2) FROM Mood m2 WHERE m2.emotion='annoyed'AND YEAR(m2.reg_date) = YEAR(:reg_date)   AND MONTH(m.reg_date)=MONTH(m2.reg_date) AND m2.userId = :userId) AS annoyed, " +
            "(SELECT count(m2) FROM Mood m2 WHERE m2.emotion='depressed'AND YEAR(m2.reg_date) = YEAR(:reg_date) AND MONTH(m.reg_date)=MONTH(m2.reg_date) AND m2.userId = :userId) AS depressed," +
            "(SELECT count(m2) FROM Mood m2 WHERE m2.emotion='sad' AND YEAR(m2.reg_date) = YEAR(:reg_date) AND MONTH(m.reg_date)=MONTH(m2.reg_date) AND m2.userId = :userId) AS sad " +
            "FROM Mood m WHERE YEAR(m.reg_date) = YEAR(:reg_date) AND MONTH(m.reg_date)=MONTH(:reg_date) AND m.userId = :userId GROUP BY MONTH(m.reg_date), YEAR(m.reg_date)")
    List<List> findMonthlyStaticsByReg_date(@Param("reg_date") LocalDateTime yearTodate, @Param("userId") String userId);

    /**
     * 검색하기
      * @param keyword
     */
    @EntityGraph(attributePaths = {"imageFiles"})
    List<Mood> findByContentsContaining(String keyword);
}
