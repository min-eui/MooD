package mood.moodmyapp.service;

import mood.moodmyapp.common.FileStore;
import mood.moodmyapp.domain.*;
import mood.moodmyapp.repository.IsLikeRepository;
import mood.moodmyapp.repository.MemberRepository;
import mood.moodmyapp.repository.MoodRepository;
import mood.moodmyapp.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MoodService {

    private final MoodRepository moodRepository;
    private final FileStore fileStore;
    private final IsLikeRepository isLikeRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;


    @Autowired
    public MoodService(MoodRepository moodRepository, FileStore fileStore, IsLikeRepository isLikeRepository, MemberRepository memberRepository, ReplyRepository replyRepository) {
        this.moodRepository = moodRepository;
        this.fileStore = fileStore;
        this.isLikeRepository = isLikeRepository;
        this.memberRepository = memberRepository;
        this.replyRepository = replyRepository;
    }

    /**
     * 글 저장하기
     * @param moodForm
     * @return
     * @throws IOException
     */
    @Transactional
    public Mood saveMood(MoodForm moodForm) throws IOException {

        Mood mood = new Mood();

        // moodForm에서 moodNum 있는지 체크
        if(moodForm.getMoodNum()!=null){
            Optional<Mood> isMoodOp = moodRepository.findById(moodForm.getMoodNum());

            // 해당 글 번호가 존재하면 업데이트 처리
            if(isMoodOp.isPresent()){
                mood = isMoodOp.get();
                mood.setContents(moodForm.getContents());
                mood.setEmotion(moodForm.getEmotion());
                List<UploadFile> storeImageFiles = fileStore.storeFiles(moodForm.getImageFiles());
                mood.setImageFiles(storeImageFiles);

                return mood;
            }
        }

        List<UploadFile> storeImageFiles = fileStore.storeFiles(moodForm.getImageFiles());

        //데이터베이스에 저장
        mood.setImageFiles(storeImageFiles);

        mood = Mood.builder()
                .userId(moodForm.getUserId())
                .userProfile(moodForm.getUserProfile())
                .emotion(moodForm.getEmotion())
                .contents(moodForm.getContents())
                .imageFiles(storeImageFiles)
                .build();

        Mood isSaved = moodRepository.save(mood);

        return isSaved;
    }

    /**
     * 메인 페이지
     * 모든 글 리스트 조회
     * @return moodList
     */
    public List<Mood> findAllMood() {

        List<Mood> moodList = moodRepository.findAllOrderByReg_dateDesc();
        return moodList;
    }


    /**
     * 글 수정
     * @param moodNum
     * @return moodPage
     */

    public Mood findByMoodNum(Long moodNum) {

        Mood moodPage = moodRepository.findByMoodNum(moodNum);
        return moodPage;

    }

    /**
     * 글 삭제
     * @param moodNum
     * @return
     */
    public int deleteByMoodNum(Long moodNum) {
        // 자식이 있으면 for 문으로 다 찾기
        List<IsLike> isLikeList = isLikeRepository.findByMoodNum(moodNum);
        List<Reply> replyList = replyRepository.findByMoodNum(moodNum);

        // 자식 칼럼이 있는지 찾기
        // 이 글에 있는 좋아요 찾아 지우기
        for(IsLike i : isLikeList){
            if(!isLikeList.isEmpty()){
                isLikeRepository.deleteAllInBatch(isLikeList);
            }
        }
        // 이 글에 달린 댓글 찾아 지우기
        for(Reply r : replyList){
            if(!replyList.isEmpty()){
                replyRepository.deleteAllInBatch(replyList);
            }
        }

        int isDel = moodRepository.deleteByMoodNum(moodNum);
        return isDel;
    }

    /**
     * 좋아요 처리
     */
    public void likeProc(Long moodNum, int isLike, String likeUserId){
        //moodNum 으로 해당 객체 찾아와서 그 객체의 좋아요 수 변경 및 좋아요 테이블 값도 세팅

        Optional<Mood>checkMoodNum = Optional.ofNullable(moodRepository.findByMoodNum(moodNum));
        Optional<IsLike> checkIsLike = Optional.ofNullable(isLikeRepository.findByMoodNumAndlikeUserId(moodNum, likeUserId));
        System.out.println("###############MoodService MoodNum = "+moodNum);
        System.out.println("###############MoodService 좋아요한 userId = "+likeUserId);
        // 글넘버가 존재하면
        if(checkMoodNum.isPresent()){
            // 좋아요 데이터가 없으면 저장
            if(checkIsLike.isEmpty()){
                IsLike saveLike = new IsLike();
                saveLike = IsLike.builder()
                        .moodNum(moodNum)
                        .likeUserId(likeUserId)
                        .isLike(isLike)
                        .build();
                isLikeRepository.save(saveLike);
                // mood의 totalLike 에 +1 해주기
                moodRepository.addTotalLike(moodNum);
            }else{
                //좋아요값이 있으면 업데이트
                isLikeRepository.updateBymoodNumAndlikeUserId(moodNum, isLike, likeUserId);

                // mood의 totalLike 에 +1 해주기
                moodRepository.addTotalLike(moodNum);
            }
        }


    }

    /**
     * 좋아요 취소
     */
    public void likeCancel(Long moodNum, int isLike, String likeUserId) {
        Optional<IsLike>checkIsLike = Optional.ofNullable(isLikeRepository.findByMoodNumAndlikeUserId(moodNum,likeUserId));

        System.out.println("###############MoodService MoodNum = "+moodNum);
        System.out.println("###############MoodService 좋아요한 userId = "+likeUserId);
        if(checkIsLike.isPresent()){
            isLikeRepository.updateBymoodNumAndlikeUserId(moodNum, isLike, likeUserId);

            // mood의 totalLike 에 -1 해주기
            moodRepository.subtractTotalLike(moodNum);

        }

    }

    /**
     * 월별 감정 통계 데이터 조회
     * @return
     */
    public List<List>  findMonthlyStatics(String searchYear, String userId) {
        searchYear = searchYear+"-01 00:00:00.000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime yearTodate = LocalDateTime.parse(searchYear,formatter);
        List<List> staticMList = moodRepository.findMonthlyStaticsByReg_date(yearTodate, userId);
        return staticMList;
    }

    public List<Mood> searchKeyword(String keyword) {

        List<Mood> searchList = moodRepository.findByContentsContainingOrderByMoodNumDesc(keyword);
        return searchList;
    }

    /**
     * 마이페이지 내가 쓴 글 보여주기
     * @param userId
     * @return
     */
    public List<Mood> findMyPosting(String userId) {
       List<Mood> myPostList =  moodRepository.findByUserId(userId);
        return myPostList;
    }
//    /**
//     * 년도별 감정 통계 데이터 조회
//     */
//    public List<List> findYearlyStatics(String searchYear) {
//        searchYear = searchYear+"-01-01 00:00:00.000";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        LocalDateTime yearTodate = LocalDateTime.parse(searchYear,formatter);
//        List<List> staticYList = moodRepository.findYearlyStaticsByReg_date(yearTodate);
//
//        return staticYList;
//    }
}
