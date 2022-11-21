package mood.moodmyapp.repository;

import mood.moodmyapp.domain.Reply;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends ReplyJpaRepository{

    /**
     * 댓글 저장하기
     * @param replyForm
     */
    Reply save(Reply replyForm);
}
