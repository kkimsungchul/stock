package com.sungchul.stock.parsing;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;


@Slf4j
@AllArgsConstructor
@Service("parsingService")
public class ParsingService {

    @PostConstruct  //프로젝트 실행시 해당 메소드를 바로 실행
    public String aa(){
        return "abceef";
    }




    public HashMap<String,String> parsingOneStock(String stockCode)throws Exception{
        HashMap<String,String> hashMap = new HashMap<>();
        //String url = "https://finance.naver.com/item/fchart.naver?code=005930";
        String url = "https://finance.naver.com/item/fchart.naver?code="+stockCode;
        //HTML페이지 전체를 가져옴
        Document doc = Jsoup.connect(url).get();
        //가져온 HTMl문서에서 ID가 wrap인 태그에 속하는 부분만 가져옴
        Elements elements = doc.select("#wrap").select("dl").get(0).select("dd");
        //elements 에서 원하는 조건을 찾아서 가져옴
        System.out.println("#### 기준 : " + elements.get(0).text());
        System.out.println("#### 종목명 : " + elements.get(1).text());
        System.out.println("#### 종목코드 : " + elements.get(2).text());
        System.out.println("#### 현재가 : " + elements.get(3).text());
        System.out.println("#### 전일가 : " + elements.get(4).text());
        System.out.println("#### 시가 : " + elements.get(5).text());
        System.out.println("#### 고가 : " + elements.get(6).text());
        System.out.println("#### 상한가 : " + elements.get(7).text());
        System.out.println("#### 저가 : " + elements.get(8).text());
        System.out.println("#### 하한가 : " + elements.get(9).text());
        System.out.println("#### 거래량 : " + elements.get(10).text());
        System.out.println("#### 거래대금 : " + elements.get(11).text());
        hashMap.put("기준",elements.get(0).text());
        hashMap.put("종목명",elements.get(1).text());
        hashMap.put("종목코드",elements.get(2).text());
        hashMap.put("현재가",elements.get(3).text());
        hashMap.put("전일가",elements.get(4).text());
        hashMap.put("시가",elements.get(5).text());
        hashMap.put("고가",elements.get(6).text());
        hashMap.put("상한가",elements.get(7).text());
        hashMap.put("저가",elements.get(8).text());
        hashMap.put("하한가",elements.get(9).text());
        hashMap.put("거래량",elements.get(10).text());
        hashMap.put("거래대금",elements.get(11).text());

        //System.out.println("#### 가격 : " + elements.select(".no_today").select(".blind").text());

        return hashMap;
    }
}
