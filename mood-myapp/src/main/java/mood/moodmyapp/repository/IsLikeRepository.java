package mood.moodmyapp.repository;

import mood.moodmyapp.domain.IsLike;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IsLikeRepository extends IsLikeJpaRepository{

    IsLike save(IsLike saveLike);

    /**
     * 좋아요 처리
     * @param moodNum
     * @param isLike
     * @param likeUserId
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE IsLike i SET i.isLike=:isLike WHERE i.moodNum =:moodNum AND i.likeUserId =:likeUserId ")
    int updateBymoodNumAndlikeUserId(@Param("moodNum")Long moodNum, @Param("isLike") int isLike, @Param("likeUserId") String likeUserId);

    @Query("SELECT i FROM IsLike i WHERE i.moodNum =:moodNum AND i.likeUserId =:likeUserId")
    IsLike findByMoodNumAndlikeUserId(@Param("moodNum")Long moodNum, @Param("likeUserId") String likeUserId);

    //해당 글번호에 있는 좋아요있는지 모두 찾기
    @Query("SELECT i FROM IsLike i WHERE i.moodNum =:moodNum")
    List<IsLike> findByMoodNum(Long moodNum);

//    @Query("SELECT i FROM IsLike i LEFT JOIN Mood m ON m.moodNum = i.moodNum ORDER BY m.reg_date DESC")
//    List<IsLike> findAllIsLike();
}
