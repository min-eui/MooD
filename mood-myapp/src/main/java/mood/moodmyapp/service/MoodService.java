package mood.moodmyapp.service;

import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.jpaEntity.BaseTimeEntity;
import mood.moodmyapp.repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MoodService {

    private final MoodRepository moodRepository;


    @Autowired
    public MoodService(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }
    @Transactional
    public Mood saveMood(Mood moodForm) {

        Mood mood = new Mood();
        // moodForm에서 moodNum 있는지 체크
        if(moodForm.getMoodNum()!=null){
            Optional<Mood> isMoodOp = moodRepository.findById(moodForm.getMoodNum());

            // 해당 글 번호가 존재하면 업데이트 처리
            if(isMoodOp.isPresent()){
                mood = isMoodOp.get();
                mood.setContents(moodForm.getContents());
                mood.setEmotion(moodForm.getEmotion());
                mood.setPhoto(moodForm.getPhoto());

                return mood;
            }
        }


        mood = Mood.builder()
                .userId(moodForm.getUserId())
                .userProfile(moodForm.getUserProfile())
                .emotion(moodForm.getEmotion())
                .contents(moodForm.getContents())
                .photo(moodForm.getPhoto())
                .likers(moodForm.getLikers())
                .viewers(moodForm.getViewers())
                .build();

        Mood isSaved = moodRepository.save(mood);

        return isSaved;
    }

    public List<Mood> findAllMood() {

        List<Mood> moodList = moodRepository.findAllOrderByReg_dateDesc();

        return moodList;
    }

    /**
     *
     * @param moodNum
     * @return moodPage
     */

    public Mood findByMoodNum(Long moodNum) {

        Mood moodPage = moodRepository.findByMoodNum(moodNum);
        return moodPage;

    }

    public int deleteByMoodNum(Long moodNum) {
        int isDel = moodRepository.deleteByMoodNum(moodNum);
        return isDel;
    }

}
