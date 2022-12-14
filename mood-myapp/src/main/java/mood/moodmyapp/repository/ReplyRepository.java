package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Reply;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReplyRepository extends ReplyJpaRepository{

    /**
     * 댓글 저장하기
     * @param replyForm
     */
    Reply save(Reply replyForm);

    /**
     * 댓글 불러오기
     * @param moodNum
     * @return
     */
    @Query("SELECT r FROM Reply r WHERE r.moodNum= :moodNum ORDER BY r.reg_date DESC ")
    List<Reply> findAllByMoodNum(@Param("moodNum") Long moodNum);

    /**
     * 댓글 삭제하기
     * @param replyNum
     * @return
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Reply r WHERE r.replyNum = :replyNum")
    int deleteByReplyNum(Long replyNum);


    /**
     * 게시글 삭제시 게시글 번호(moodNum)으로 댓글들 찾기
     * @param moodNum
     * @return
     */
    List<Reply> findByMoodNum(Long moodNum);
}
