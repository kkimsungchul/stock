package com.sungchul.stock.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;



@Slf4j
@AllArgsConstructor
@Service("dateService")
public class DateService {
    /*yyyymmdd 로 현재 날짜 리턴*/
    public String getDate() {
        String value = "";
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        value = formatter.format(new java.util.Date());
        return value;
    }

    /*yyyy 로 현재 연도 리턴*/
    public String getYear() {
        String value = "";
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy");
        value = formatter.format(new java.util.Date());
        return value;
    }
    /*HHmmss 로 현재 시간 리턴*/
    public String getTime() {
        String value = "";
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("HHmmss");
        value = formatter.format(new java.util.Date());
        return value;
    }
    /*지정한 형식으로 출력*/
    public String getTime(String strformat){
        String value = "";
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(strformat);
        value = formatter.format(new java.util.Date());
        return value;
    }

    /*
     * 현재날짜에 변수로 받은 일 또는 년도를 더해서 리턴
     * @Param nType : 1 : 년, 2 : 월 , 5 : 일
     * */
    public String getTime(String strformat,int add,int nType ){
        String value = "";
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(strformat);


        Calendar today = Calendar.getInstance();
        today.add(nType, add);
        value = formatter.format(today.getTime());
        return value;
    }
}
