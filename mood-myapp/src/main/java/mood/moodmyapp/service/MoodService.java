package mood.moodmyapp.service;

import mood.moodmyapp.domain.Mood;
import mood.moodmyapp.jpaEntity.BaseTimeEntity;
import mood.moodmyapp.repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        mood = Mood.builder()
                .userId(moodForm.getUserId())
                .mood(moodForm.getMood())
                .contents(moodForm.getContents())
                .photo(moodForm.getPhoto())
                .likers(moodForm.getLikers())
                .viewers(moodForm.getViewers())
                .build();

        Mood isSaved = moodRepository.save(mood);

        return isSaved;
    }

    public List<Mood> findAllMood() {

        List<Mood> moodList = moodRepository.findAll();

        return moodList;
    }
}
