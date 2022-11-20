package mood.moodmyapp.controller;

import mood.moodmyapp.service.StaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class StaticsController {
    private final StaticsService staticsService;

    public StaticsController(StaticsService staticsService) {
        this.staticsService = staticsService;
    }




//    /**
//     * 월별 감정 통계 데이터 조회
//     */
//
//    @ResponseBody
//    @PostMapping("/statics/monthly/EmotionDataType.do")
//    public Map<String,Object> emotionDataType(String searchYear){
//        HashMap<String,Object> staticMap = staticsService.emotionDataType(searchYear);
////        HashMap<String,Object> staticMap = new HashMap<>();
//        return staticMap;
//    }





}
