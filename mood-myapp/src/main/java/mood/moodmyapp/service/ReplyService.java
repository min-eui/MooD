package mood.moodmyapp.service;

import mood.moodmyapp.domain.Reply;
import mood.moodmyapp.repository.MemberRepository;
import mood.moodmyapp.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {


    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    public ReplyService(ReplyRepository replyRepository, MemberRepository memberRepository) {
        this.replyRepository = replyRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 댓글작성하기
     * @param replyForm
     */
    public boolean saveReply(Reply replyForm) {
        //userId로 nickName 찾아서 값 넣어주기
        //replyForm.setNickName(memberRepository.findByUserId(replyForm.getRegistrant()));
        Reply isReply =  replyRepository.save(replyForm);
        if(isReply!=null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * MoodNum에 달린 모든 댓글 조회
     * @param moodNum
     * @return
     */
    public List<Reply> findAllByMoodNum(Long moodNum) {
        List<Reply> replyList =  replyRepository.findAllByMoodNum(moodNum);
        return replyList;
    }
    /**
     * replyNum과 일치하는 댓글 삭제
     * @param replyNum
     * @return
     */
    public int deleteByReplyNum(Long replyNum) {
        int isDel = replyRepository.deleteByReplyNum(replyNum);
        return isDel;
    }
}
