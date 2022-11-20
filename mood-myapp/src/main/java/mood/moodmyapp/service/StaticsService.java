package mood.moodmyapp.service;

import mood.moodmyapp.repository.StaticsRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StaticsService {

    private final StaticsRepository staticsReposiotory;

    public StaticsService(StaticsRepository staticsReposiotory) {
        this.staticsReposiotory = staticsReposiotory;
    }

//    public HashMap<String, Object> emotionDataType(String searchYear) {
//        HashMap<String, Object> staticsMap = staticsReposiotory.findByStatics_reg_date(searchYear);
//        return staticsMap;
//    }
}
