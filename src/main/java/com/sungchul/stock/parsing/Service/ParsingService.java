package com.sungchul.stock.parsing.Service;


import com.sungchul.stock.csv.service.CSVService;
import com.sungchul.stock.parsing.mapper.ParsingMapper;
import com.sungchul.stock.parsing.vo.ParsingScheduleVO;
import com.sungchul.stock.parsing.vo.ParsingVO;
import com.sungchul.stock.stockData.vo.StockVO;
import com.sungchul.stock.stockData.mapper.StockDataMapper;
import com.sungchul.stock.common.util.DateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Slf4j
@AllArgsConstructor
@Service("parsingService")
public class ParsingService {

    CSVService csvService;

    DateService dateService;

    ParsingMapper parsingMapper;

    StockDataMapper stockDataMapper;

    @PostConstruct  //프로젝트 실행시 해당 메소드를 바로 실행
    public String aa(){
        return "abceef";
    }


    /**
     * CSV 저장된 주식종목 코드목록으로 parsingOneStock 메소드를 호출하여 실행시에 해당하는 주식 정보를 파싱
     * @return List<ParsingVO>
     * */
    public List<ParsingVO> parsingAllStock() throws Exception{
        List<List<String>> stockList = csvService.readStockList();
        List<ParsingVO> parsingList = new ArrayList<>();
        StockVO stockVO = new StockVO();
        for(List list: stockList){
            stockVO.setStockCode(list.get(0).toString());
            parsingList.add(parsingOneStock(stockVO,2));
        }
        return parsingList;
    }





    /**
     * DB에 저장된 주식종목 코드목록으로 parsingOneStock 메소드를 호출
     * @return int
     * */
    public int saveParsingData() throws Exception{
        ParsingScheduleVO parsingScheduleVO = new ParsingScheduleVO();
        StopWatch stopWatch = new StopWatch();

        //주말에는 작동하지 않게 할거임
        //현재는 매일수동으로 하고있어서 미필요함..
//        if(dateService.getWeekDay()== 1 || dateService.getWeekDay()== 7){
//            return 0;
//        }

        //작동한 적이 있으면 다시 작동하지 않도록 설정
        if(getParsingScheduleLog().size()!=0) {
            return 0;
        };

        stopWatch.start();
        parsingScheduleVO.setStartTime(dateService.getTime("yyyyMMddHHmmss"));

        List<StockVO> stockList = stockDataMapper.getStockCode();
        int insertCouint=0;
        for(StockVO stockVO: stockList){
            parsingOneStock(stockVO,1);
            insertCouint++;
        }

        parsingScheduleVO.setEndTime(dateService.getTime("yyyyMMddHHmmss"));
        stopWatch.stop();
        parsingScheduleVO.setElapsedTime(stopWatch.getTotalTimeMillis());
        parsingScheduleVO.setScheduleDate(dateService.getTime("yyyyMMdd"));
        saveParsingScheduleLog(parsingScheduleVO);

        return 1;
    }


    /**
     * 전달받은 주식종목 정보를 토대로 데이터를 파싱하여 DB에 저장
     * @param stockVO
     * @param flag , 구분값 1: DB 저장 , 2 : 한종목 파싱
     * @return ParsingVO
     * */
    public ParsingVO parsingOneStock(StockVO stockVO , int flag)throws Exception{

        ParsingVO parsingVO = new ParsingVO();
        LinkedHashMap<String,String> hashMap = new LinkedHashMap<>();
        String url = "https://finance.naver.com/item/main.naver?code="+stockVO.getStockCode();

        parsingVO.setStockCode(stockVO.getStockCode());
        parsingVO.setParsingDate(dateService.getTime("yyyyMMdd"));
        parsingVO.setParsingDateDetail(dateService.getTime("yyyyMMddHHmmss"));

        //HTML페이지 전체를 가져옴
        Document doc = Jsoup.connect(url).get();
        //가져온 HTMl문서에서 ID가 wrap인 태그에 속하는 부분만 가져옴
        try {

            if(doc.select("#wrap").select("dl").toString().length()==0){
                parsingVO.setParsingMemo("해당 주식정보가 존재하지 않음");
                if(flag==1) {
                    parsingMapper.insertParsingData(parsingVO);
                }
                return parsingVO;
            }
            //주식 기본 정보
            Elements elements = doc.select("#wrap").select("dl").get(0).select("dd");

            //금일 주식 정보를 가져옴

            Element elementToday = doc.select(".today").get(0);

            String []today = elementToday.text().split("\\s");
            /*
            System.out.println(elementToday);
            System.out.println(elementToday.text());
            * today[0] : 현재가
            * today[1] : 현재가
            * today[2] : 전일대비 (제목)
            * today[3] : 상승,하락가격
            * today[4] : 상승 하락 가격
            * today[8] : 상승 하락 퍼센트
            */
            parsingVO.setDirection(today[3]);
            //directionCode
            parsingVO.setPriceGap(conversionINT(today[4]));
            if(today[3].equals("상승")){
                parsingVO.setDirectionCode(1);
            }else if(today[3].equals("하락")){
                parsingVO.setDirectionCode(2);
                //하향일 경우 음수로 전환
                parsingVO.setPriceGap(parsingVO.getPriceGap()*-1);
            }else if(today[3].equals("보합")){
                parsingVO.setDirectionCode(3);
            }else{
                parsingVO.setDirectionCode(0);
            }
            //외인 기관 매수정보
            //외인 기관 매수정보가 없을 경우 오류발생 예방
            if(doc.select("div.sub_section.right table.tb_type1 tbody tr").size()>1){
                Element element = doc.select("div.sub_section.right table.tb_type1 tbody tr").get(1);
                parsingVO.setForeignTrade(conversionINT(element.select("td em").get(2).text()));
                parsingVO.setInstitutionTrade(conversionINT(element.select("td em").get(3).text()));


            }else{
                parsingVO.setDirection("");
                parsingVO.setForeignTrade(0);
                parsingVO.setInstitutionTrade(0);
            }
            String []stockName = elements.get(1).text().split("\\s");
            //주식 이름이 띄어쓰기가 있는 경우가 있어서 작성함
            if(stockName.length>2){
                String tempName="";
                for(int i=1; i<stockName.length;i++){
                    tempName += stockName[i];
                    if(i!=stockName.length-1){
                        tempName+=" ";
                    }
                }
                parsingVO.setStockName(tempName);
            }else{
                parsingVO.setStockName(stockName[1]);
            }
            String []stockCategory = elements.get(2).text().split("\\s");
            if(stockCategory.length>2){
                if(stockCategory[2].equals("코스피")){
                    parsingVO.setStockCategoryCode(1);
                    parsingVO.setStockCategoryName(stockCategory[2]);
                }else if(stockCategory[2].equals("코스닥")){
                    parsingVO.setStockCategoryCode(2);
                    parsingVO.setStockCategoryName(stockCategory[2]);
                }
            }else{
                parsingVO.setStockCategoryCode(3);
                parsingVO.setStockCategoryName("코넥스");
            }

            String []presentPrice = elements.get(3).text().split("\\s");
            parsingVO.setPresentPrice(Integer.parseInt(presentPrice[1].replace(",","")));


            String []yesterdayPrice = elements.get(4).text().split("\\s");
            parsingVO.setYesterdayPrice(Integer.parseInt(yesterdayPrice[1].replace(",","")));


            String []currentPrice = elements.get(5).text().split("\\s");
            parsingVO.setCurrentPrice(Integer.parseInt(currentPrice[1].replace(",","")));


            String []highPrice = elements.get(6).text().split("\\s");
            parsingVO.setHighPrice(Integer.parseInt(highPrice[1].replace(",","")));


            String []upperLimitPrice = elements.get(7).text().split("\\s");
            parsingVO.setUpperLimitPrice(Integer.parseInt(upperLimitPrice[1].replace(",","")));


            String []lowPrice = elements.get(8).text().split("\\s");
            parsingVO.setLowPrice(Integer.parseInt(lowPrice[1].replace(",","")));


            String []lowerLimitPrice = elements.get(9).text().split("\\s");
            parsingVO.setLowerLimitPrice(Integer.parseInt(lowerLimitPrice[1].replace(",","")));


            String []tradingVolume = elements.get(10).text().split("\\s");
            parsingVO.setTradingVolume(Integer.parseInt(tradingVolume[1].replace(",","")));


            String []tradingValue = elements.get(11).text().split("\\s");
            parsingVO.setTradingValue(tradingValue[1]);


            //per eps가져옴
            if(doc.select(".per_table tbody tr td").size()>=1) {
                Element elementPer = doc.select(".per_table tbody tr td").get(0);
                parsingVO.setPer(conversionDouble(elementPer.select("em").get(0).text()));
                parsingVO.setEps(conversionDouble(elementPer.select("em").get(1).text()));
            }

        }catch (Exception e){
            log.info("#### Exception : {}" , e);
            System.out.println(parsingVO);
        }
        if(flag==1){
            parsingMapper.insertParsingData(parsingVO);
        }

        return parsingVO;
    }


    /**
     * 전달받은 문자열에서 숫자를 제외한 문자를 제거한 후 int로 변환하여 리턴
     * @param str
     * @return int
     * */
    public int conversionINT(String str){
        int minusCheck=1;
        if(str.equals("") || str.isEmpty()){
            return 0;
        }else{
            //음수 체크
            if(str.contains("-")){
                minusCheck = -1;
            }
            String restr = str.replaceAll("[^0-9]","");
            //문자열을 숫자로 변환 후 비어있을 경우 0으로 치환
            if(restr.equals("") || restr.isEmpty()){
                return 0;
            }
            return Integer.parseInt(restr)*minusCheck;
        }
    }

    /**
     * 전달받은 문자열에서 숫자를 제외한 문자를 제거한 후 double로 변환하여 리턴
     * @param str
     * @return double
     * */
    public double conversionDouble(String str){
        double minusCheck=1;

        //넘어온 문자열 공백 빈값 확인
        if(str.equals("") || str.isEmpty()){
            return 0;
        }else{
            //음수 체크
            if(str.contains("-")){
                minusCheck = -1;
            }
            String restr = str.replaceAll("[^0-9\\.]","");
            //문자열을 숫자로 변환 후 비어있을 경우 0으로 치환
            if(restr.equals("") || restr.isEmpty()){
                return 0;
            }
            return Double.parseDouble(restr)*minusCheck;
        }
    }






    /**
     * 테스트
     * @param stockCode
     * @return LinkedHashMap<String,String>
     */
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

    /**
     * 파싱 작업 로그 저장
     * @param parsingScheduleVO
     * @return viod
     */
    public void saveParsingScheduleLog(ParsingScheduleVO parsingScheduleVO){


        parsingMapper.saveParsingScheduleLog(parsingScheduleVO);
    }


    /**
     * 금일 파싱 작업을 실행한 로그가 있는지 확인
     * @return List<ParsingScheduleVO>
     */
    public List<ParsingScheduleVO> getParsingScheduleLog() {
        ParsingScheduleVO parsingScheduleVO = new ParsingScheduleVO();
        parsingScheduleVO.setScheduleDate(dateService.getTime("yyyyMMdd"));
        //parsingScheduleVO.setScheduleDate("20220110");



        return parsingMapper.getParsingScheduleLog(parsingScheduleVO);
    }

    public void mapperTest(){


//        ParsingScheduleVO parsingScheduleVO = new ParsingScheduleVO();
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        parsingScheduleVO.setStartTime(dateService.getTime("yyyyMMddHHmmss"));
//        System.out.println(parsingMapper.test());
//        parsingScheduleVO.setEndTime(dateService.getTime("yyyyMMddHHmmss"));
//        stopWatch.stop();
//        parsingScheduleVO.setElapsedTime(stopWatch.getTotalTimeMillis());
//        parsingScheduleVO.setScheduleDate(dateService.getTime("yyyyMMdd"));
//        saveParsingScheduleLog(parsingScheduleVO);


    }

    public List<ParsingVO> mapperTest2(StockVO stockVO){
        return parsingMapper.getDateTest(stockVO);

    }





    /*원격 파일다운로드 시작 , 사용하지 않고 있음*/
    public String stockListDownload(){
        String url="http://data.krx.co.kr//comm/fileDn/download_csv/download.cmd";
        String dir="c:\\temp";
        try {
            downloadToDir(new URL(url), new File(dir));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
    /** 정해진 file로 url의 내용을 저장한다. (저장되는 파일명은 url과 무관함)  **/
    public void downloadToFile(URL url, File savedFile) throws IOException {
        if (url==null) throw new IllegalArgumentException("url is null.");
        if (savedFile==null) throw new IllegalArgumentException("savedFile is null.");
        if (savedFile.isDirectory()) throw new IllegalArgumentException("savedFile is a directory.");
        downloadTo(url, savedFile, false);
    }

    /** 정해진 디렉토리로 url의 내용을 저장한다. (저장되는 파일명이 url에 따라서 달라짐) **/
    public void downloadToDir(URL url, File dir) throws IOException {
        if (url==null) throw new IllegalArgumentException("url is null.");
        if (dir==null) throw new IllegalArgumentException("directory is null.");
        if (!dir.exists()) throw new IllegalArgumentException("directory is not existed.");
        if (!dir.isDirectory()) throw new IllegalArgumentException("directory is not a directory.");
        downloadTo(url, dir, true);
    }

    private void downloadTo(URL url, File targetFile, boolean isDirectory) throws IOException{

        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            File saveFilePath=null;

            if (isDirectory) {
                if (disposition != null) {
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10,
                                disposition.length() - 1);
                    }
                } else {
                    String fileURL=url.toString();
                    fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
                    int questionIdx=fileName.indexOf("?");
                    if (questionIdx>=0) {
                        fileName=fileName.substring(0, questionIdx);
                    }
                    fileName= URLDecoder.decode(fileName);
                }
                saveFilePath = new File(targetFile, fileName);
            }
            else {
                saveFilePath=targetFile;
            }

            InputStream inputStream = httpConn.getInputStream();

            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
            System.out.println("File downloaded to " + saveFilePath);
        } else {
            System.err.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
    /*원격 파일다운로드 종료 , 사용하지 않고 있음*/
}
