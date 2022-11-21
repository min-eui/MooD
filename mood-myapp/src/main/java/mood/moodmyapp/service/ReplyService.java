package mood.moodmyapp.service;

import mood.moodmyapp.domain.Reply;
import mood.moodmyapp.repository.ReplyRepository;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {


    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    /**
     * 댓글작성하기
     * @param replyForm
     */
    public boolean saveReply(Reply replyForm) {
        Reply isReply =  replyRepository.save(replyForm);
        if(isReply!=null){
            return true;
        }else{
            return false;
        }
    }
}
