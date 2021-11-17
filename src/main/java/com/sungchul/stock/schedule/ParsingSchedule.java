package com.sungchul.stock.schedule;

import com.sungchul.stock.parsing.Service.ParsingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ParsingSchedule {

    @Autowired
    ParsingService parsingService;

//    @Scheduled(cron = "*/10 * * * * MON-FRI", zone = "Asia/Seoul")
//    public void test(){
//        log.info("테스트111");
//    }

    /**
     * 주식 파싱 스케줄러
     * 월-금요일까지 매일 오후 8시에 시작
     * */
    @Scheduled(cron = "0 0 20 * * MON-FRI", zone = "Asia/Seoul")
    public void parsingSchedule(){
        log.info(" 주식 전체 정보 파싱 시작");

    }

}
