package com.sungchul.stock.parsing;


import com.sungchul.stock.csv.CSVService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Service("parsingService")
public class ParsingService {

    CSVService csvService;

    @PostConstruct  //프로젝트 실행시 해당 메소드를 바로 실행
    public String aa(){
        return "abceef";
    }


    public List<LinkedHashMap<String,String>> parsingAllStock() throws Exception{
        List<List<String>> stockList = csvService.readToList();
        List<LinkedHashMap<String,String>> parsingList = new ArrayList<>();
        for(List list: stockList){
            parsingList.add(parsingOneStock(list.get(0).toString()));
        }


        return parsingList;
    }

    //https://finance.naver.com/item/main.nhn?code=197140
    //@PostConstruct
    public void test() throws Exception{
        String url = "https://finance.naver.com/item/main.naver?code=005930";
        //HTML페이지 전체를 가져옴
        Document doc = Jsoup.connect(url).get();
        //가져온 HTMl문서에서 ID가 wrap인 태그에 속하는 부분만 가져옴
        //Elements elements = doc.select("div.sub_section");
        //Elements elements = doc.select("div.sub_section.right table.tb_type1 tbody tr td");
        Elements elements = doc.select("div.sub_section.right table.tb_type1 tbody tr");

        System.out.println(elements.get(1).select("th").text());            //날짜
        System.out.println(elements.get(1).select("td em").get(0).text());//가격
        System.out.println(elements.get(1).select("td em").get(1).text());//가격
        System.out.println(elements.get(1).select("td em").get(2).text());//가격
        System.out.println(elements.get(1).select("td em").get(3).text());//가격


        System.out.println("##########################");

        //외인 동향을 가져옴
        Element element = doc.select("div.sub_section.right table.tb_type1 tbody tr").get(1);

        System.out.println(element.select("th").text());            //날짜
        System.out.println(element.select("td em").get(0).text());//가격
        System.out.println(element.select("td em").get(1).text());//가격
        System.out.println(element.select("td em").get(2).text());//가격
        System.out.println(element.select("td em").get(3).text());//가격



    }


    public LinkedHashMap<String,String> parsingOneStock(String stockCode)throws Exception{

        LinkedHashMap<String,String> hashMap = new LinkedHashMap<>();
        //String url = "https://finance.naver.com/item/fchart.naver?code=005930";
        String url = "https://finance.naver.com/item/main.naver?code="+stockCode;
        //String url = "https://finance.naver.com/item/fchart.naver?code="+stockCode;

        //HTML페이지 전체를 가져옴
        Document doc = Jsoup.connect(url).get();
        //가져온 HTMl문서에서 ID가 wrap인 태그에 속하는 부분만 가져옴
        Elements elements = doc.select("#wrap").select("dl").get(0).select("dd");

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


        //외국인 , 기관 동향을 가져옴
        Element element = doc.select("div.sub_section.right table.tb_type1 tbody tr").get(1);

//        System.out.println(element.select("th").text());           //날짜
//        System.out.println(element.select("td em").get(0).text());//가격
//        System.out.println(element.select("td em").get(1).text());//상승폭
//        System.out.println(element.select("td em").get(2).text());//외국인
//        System.out.println(element.select("td em").get(3).text());//기관

        hashMap.put("날짜",element.select("th").text());
        hashMap.put("상승폭",element.select("td em").get(1).text());
        hashMap.put("외국인 매매",element.select("td em").get(2).text());
        hashMap.put("기관 매매",element.select("td em").get(3).text());



        //System.out.println("#### 가격 : " + elements.select(".no_today").select(".blind").text());

        return hashMap;
    }
}
