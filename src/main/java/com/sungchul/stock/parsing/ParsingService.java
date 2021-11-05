package com.sungchul.stock.parsing;


import com.sungchul.stock.csv.CSVService;
import com.sungchul.stock.util.DateService;
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

    DateService dateService;

    @PostConstruct  //프로젝트 실행시 해당 메소드를 바로 실행
    public String aa(){
        return "abceef";
    }


    public List<ParsingVO> parsingAllStock() throws Exception{
        List<List<String>> stockList = csvService.readToList();
        List<ParsingVO> parsingList = new ArrayList<>();
        for(List list: stockList){
            System.out.println(list.get(0).toString());
            parsingList.add(parsingOneStock(list.get(0).toString()));
        }
        return parsingList;
    }


    public ParsingVO parsingOneStock(String stockCode)throws Exception{

        ParsingVO parsingVO = new ParsingVO();
        LinkedHashMap<String,String> hashMap = new LinkedHashMap<>();
        //String url = "https://finance.naver.com/item/fchart.naver?code=005930";
        String url = "https://finance.naver.com/item/main.naver?code="+stockCode;
        //String url = "https://finance.naver.com/item/fchart.naver?code="+stockCode;

        parsingVO.setStockCode(stockCode);

        //HTML페이지 전체를 가져옴
        Document doc = Jsoup.connect(url).get();
        //가져온 HTMl문서에서 ID가 wrap인 태그에 속하는 부분만 가져옴

        try {

            //주식 기본 정보
            Elements elements = doc.select("#wrap").select("dl").get(0).select("dd");

            //외인 기관 매수정보
            Element element = doc.select("div.sub_section.right table.tb_type1 tbody tr").get(1);




            hashMap.put("기준",elements.get(0).text());
            //String parsingDateDetail = elements.get(0).text().replaceAll("[^0-9]","");
            String parsingDateDetail = dateService.getTime("yyyy-MM-dd HH:mm:ss");
            parsingVO.setParsingDateDetail(parsingDateDetail);

            hashMap.put("종목명",elements.get(1).text());
            String []stockName = elements.get(1).text().split("\\s");
            parsingVO.setStockName(stockName[1]);


            hashMap.put("종목코드",elements.get(2).text());
            String []stockCategory = elements.get(2).text().split("\\s");
            parsingVO.setStockCategoryName(stockCategory[2]);
            if(stockCategory[2].equals("코스피")){
                parsingVO.setStockCategoryCode(1);
            }else if(stockCategory[2].equals("코스닥")){
                parsingVO.setStockCategoryCode(2);
            }



            hashMap.put("현재가",elements.get(3).text());
            String []presentPrice = elements.get(3).text().split("\\s");
            parsingVO.setPresentPrice(Integer.parseInt(presentPrice[1].replace(",","")));

            hashMap.put("전일가",elements.get(4).text());
            String []yesterdayPrice = elements.get(4).text().split("\\s");
            parsingVO.setYesterdayPrice(Integer.parseInt(yesterdayPrice[1].replace(",","")));

            hashMap.put("시가",elements.get(5).text());
            String []currentPrice = elements.get(5).text().split("\\s");
            parsingVO.setCurrentPrice(Integer.parseInt(currentPrice[1].replace(",","")));

            hashMap.put("고가",elements.get(6).text());
            String []highPrice = elements.get(6).text().split("\\s");
            parsingVO.setHighPrice(Integer.parseInt(highPrice[1].replace(",","")));

            hashMap.put("상한가",elements.get(7).text());
            String []upperLimitPrice = elements.get(7).text().split("\\s");
            parsingVO.setUpperLimitPrice(Integer.parseInt(upperLimitPrice[1].replace(",","")));

            hashMap.put("저가",elements.get(8).text());
            String []lowPrice = elements.get(8).text().split("\\s");
            parsingVO.setLowPrice(Integer.parseInt(lowPrice[1].replace(",","")));

            hashMap.put("하한가",elements.get(9).text());
            String []lowerLimitPrice = elements.get(9).text().split("\\s");
            parsingVO.setLowerLimitPrice(Integer.parseInt(lowerLimitPrice[1].replace(",","")));

            hashMap.put("거래량",elements.get(10).text());
            String []tradingVolume = elements.get(10).text().split("\\s");
            parsingVO.setTradingVolume(Integer.parseInt(tradingVolume[1].replace(",","")));

            hashMap.put("거래대금",elements.get(11).text());
            String []tradingValue = elements.get(10).text().split("\\s");
            parsingVO.setTradingValue(tradingValue[1]);

            if(element==null){
                return parsingVO;
            }

            hashMap.put("날짜",element.select("th").text());
            parsingVO.setParsingDate(element.select("th").text());

            hashMap.put("상승폭",element.select("td em").get(1).text());
            String []directionAndGap =element.select("td em").get(1).text().split("\\s");
            System.out.println(hashMap);
            parsingVO.setDirection(directionAndGap[0]);
            if(directionAndGap.length>1){
                parsingVO.setPriceGap(conversionBlank(directionAndGap[1]));
            }else{
                parsingVO.setPriceGap(0);
            }



            hashMap.put("외국인 매매",element.select("td em").get(2).text());
            parsingVO.setForeignTrade(conversionBlank(element.select("td em").get(2).text()));


            hashMap.put("기관 매매",element.select("td em").get(3).text());
            parsingVO.setInstitutionTrade(conversionBlank(element.select("td em").get(3).text()));
            
        }catch (Exception e){
            log.info("#### Exception : {}" , e);
        }finally {
            return parsingVO;
        }

//        System.out.println("### elements.get(0) : " + elements.get(0));
//        System.out.println("### elements.get(1) : " + elements.get(1));
//        System.out.println("### elements.get(2) : " + elements.get(2));
//        System.out.println("### elements.get(3) : " + elements.get(3));
//        System.out.println("### elements.get(4) : " + elements.get(4));
//        System.out.println("### elements.get(5) : " + elements.get(5));
//        System.out.println("### elements.get(6) : " + elements.get(6));
//        System.out.println("### elements.get(7) : " + elements.get(7));
//        System.out.println("### elements.get(8) : " + elements.get(8));
//        System.out.println("### elements.get(9) : " + elements.get(9));
//        System.out.println("### elements.get(10) : " + elements.get(10));
//        System.out.println("### elements.get(11) : " + elements.get(11));
        //외국인 , 기관 동향을 가져옴
//        System.out.println(element.select("th").text());           //날짜
//        System.out.println(element.select("td em").get(0).text());//가격
//        System.out.println(element.select("td em").get(1).text());//상승폭
//        System.out.println(element.select("td em").get(2).text());//외국인
//        System.out.println(element.select("td em").get(3).text());//기관




        //System.out.println("#### 가격 : " + elements.select(".no_today").select(".blind").text());
       }


    public int conversionBlank(String str){
        if(str.equals("") || str.isEmpty()){
            return 0;
        }else{
            String restr = str.replaceAll("[^0-9]","");
            return Integer.parseInt(restr);
        }
    }


    //https://finance.naver.com/item/main.nhn?code=197140
    //@PostConstruct
    public LinkedHashMap<String,String> test(String stockCode) throws Exception{

        LinkedHashMap<String,String> hashMap = new LinkedHashMap<>();
        //String url = "https://finance.naver.com/item/fchart.naver?code=005930";
        String url = "https://finance.naver.com/item/main.naver?code="+stockCode;
        //String url = "https://finance.naver.com/item/fchart.naver?code="+stockCode;

        //HTML페이지 전체를 가져옴
        Document doc = Jsoup.connect(url).get();
        //가져온 HTMl문서에서 ID가 wrap인 태그에 속하는 부분만 가져옴
        Elements elements = doc.select("#wrap").select("dl").get(0).select("dd");
        Element element = doc.select("div.sub_section.right table.tb_type1 tbody tr").get(1);

        hashMap.put("기준",elements.get(0).text());
        //String parsingDateDetail = elements.get(0).text().replaceAll("[^0-9]","");
        String parsingDateDetail = dateService.getTime("yyyy-mm-dd HH:mm:ss");


        hashMap.put("종목명",elements.get(1).text());
        String []stockName = elements.get(1).text().split("\\s");


        hashMap.put("종목코드",elements.get(2).text());


        hashMap.put("현재가",elements.get(3).text());
        String []currentPrice = elements.get(3).text().split("\\s");


        hashMap.put("전일가",elements.get(4).text());


        hashMap.put("시가",elements.get(5).text());


        hashMap.put("고가",elements.get(6).text());


        hashMap.put("상한가",elements.get(7).text());


        hashMap.put("저가",elements.get(8).text());


        hashMap.put("하한가",elements.get(9).text());


        hashMap.put("거래량",elements.get(10).text());


        hashMap.put("거래대금",elements.get(11).text());



        hashMap.put("날짜",element.select("th").text());


        hashMap.put("상승폭",element.select("td em").get(1).text());


        hashMap.put("외국인 매매",element.select("td em").get(2).text());


        hashMap.put("기관 매매",element.select("td em").get(3).text());

//        System.out.println("### elements.get(0) : " + elements.get(0));
//        System.out.println("### elements.get(1) : " + elements.get(1));
//        System.out.println("### elements.get(2) : " + elements.get(2));
//        System.out.println("### elements.get(3) : " + elements.get(3));
//        System.out.println("### elements.get(4) : " + elements.get(4));
//        System.out.println("### elements.get(5) : " + elements.get(5));
//        System.out.println("### elements.get(6) : " + elements.get(6));
//        System.out.println("### elements.get(7) : " + elements.get(7));
//        System.out.println("### elements.get(8) : " + elements.get(8));
//        System.out.println("### elements.get(9) : " + elements.get(9));
//        System.out.println("### elements.get(10) : " + elements.get(10));
//        System.out.println("### elements.get(11) : " + elements.get(11));


        //외국인 , 기관 동향을 가져옴

//        System.out.println(element.select("th").text());           //날짜
//        System.out.println(element.select("td em").get(0).text());//가격
//        System.out.println(element.select("td em").get(1).text());//상승폭
//        System.out.println(element.select("td em").get(2).text());//외국인
//        System.out.println(element.select("td em").get(3).text());//기관



//        Response body
//        Download
//        {
//            "기준": "2021년 11월 05일 16시 11분 기준 장마감",
//                "종목명": "종목명 삼성전자",
//                "종목코드": "종목코드 005930 코스피",
//                "현재가": "현재가 70,200 전일대비 하락 400 마이너스 0.57 퍼센트",
//                "전일가": "전일가 70,600",
//                "시가": "시가 71,600",
//                "고가": "고가 71,600",
//                "상한가": "상한가 91,700",
//                "저가": "저가 70,200",
//                "하한가": "하한가 49,500",
//                "거래량": "거래량 12,605,513",
//                "거래대금": "거래대금 889,444백만",
//                "날짜": "11/05",
//                "상승폭": "하향 400",
//                "외국인 매매": "-1,174,569",
//                "기관 매매": "-905,068"
//        }
        return hashMap;

    }
}
