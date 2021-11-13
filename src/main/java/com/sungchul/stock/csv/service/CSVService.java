package com.sungchul.stock.csv.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sungchul.stock.parsing.mapper.ParsingMapper;
import com.sungchul.stock.stockData.vo.StockVO;
import com.sungchul.stock.stockData.mapper.StockDataMapper;
import com.sungchul.stock.common.util.DateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@AllArgsConstructor
@Service("csvService")
public class CSVService {

    ParsingMapper parsingMapper;
    StockDataMapper stockDataMapper;
    DateService dateService;


    /**
     * CSV파일을 읽어와 주식 종목을 DB에 저장
     * @param void
     * @return int
     * */
    public int saveStockList(){
        File csv = new File("20211108_stockCode.csv");
        BufferedReader br = null;
        StockVO stockVO = new StockVO();
        int count = 0;
        int errorCount=0;

        try {
            br = new BufferedReader(new FileReader(csv));
            Charset.forName("UTF-8");
            String line = "";
            int i=0;
            while((line=br.readLine()) != null) {
                String[] tempToken = line.split("\",\"");
                for(int z = 0; z<tempToken.length;z++){
                    tempToken[z] = tempToken[z].replaceAll("\"","");
                    System.out.println(tempToken[z]);
                }
                System.out.println("###########");
                //배열 순서
                //표준코드,단축코드,한글 종목명,한글 종목약명,영문 종목명,상장일,시장구분,증권구분,소속부,주식종류,액면가,상장주식수
                stockVO.setStockLongCode(tempToken[0]);
                stockVO.setStockCode(tempToken[1]);
                stockVO.setStockName(tempToken[2]);
                stockVO.setStockCategoryName(tempToken[6]);

                if(tempToken[6].equals("KOSPI")){
                    stockVO.setStockCategoryCode(1);
                }else if(tempToken[6].equals("KOSDAQ")){
                    stockVO.setStockCategoryCode(2);
                }else if(tempToken[6].equals("KONEX")){
                    stockVO.setStockCategoryCode(3);
                }else{
                    stockVO.setStockCategoryCode(0);
                }


                stockVO.setInsertDate(dateService.getTime("yyyyMMddHHmmss"));
                if(parsingMapper.insertStockInfo(stockVO)==1){
                    count++;
                }else{
                    errorCount++;
                }
            }

        } catch (FileNotFoundException e) {
            log.info("Error : {}",e);
        } catch (IOException e) {
            log.info("Error : {}",e);
        } finally {
            try {
                if(br != null) {br.close();}
            } catch (IOException e) {
                log.info("Error : {}",e);
            }
        }


        return count;
    }


    /**
     * CSV파일을 읽어서 주식 코드를 리턴
     * @param void
     * @return List<List<String>>
     * */
    public List<List<String>> readStockList() {
        List<List<String>> list = new ArrayList<List<String>>();
        File csv = new File("20211108_stockCode.csv");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
            Charset.forName("UTF-8");
            String line = "";
            int i=0;
            while((line=br.readLine()) != null) {
                String[] tempToken = line.split("\",\"");
                for(int z = 0; z<tempToken.length;z++){
                    tempToken[z] = tempToken[z].replaceAll("\"","");
                }
                //배열 순서
                //표준코드,단축코드,한글 종목명,한글 종목약명,영문 종목명,상장일,시장구분,증권구분,소속부,주식종류,액면가,상장주식수
                String[] stock = {tempToken[1] ,tempToken[2]};
                List<String> tempList = new ArrayList<String>(Arrays.asList(stock));
                list.add(tempList);
            }

        } catch (FileNotFoundException e) {
            log.info("Error : {}",e);
        } catch (IOException e) {
            log.info("Error : {}",e);
        } finally {
            try {
                if(br != null) {br.close();}
            } catch (IOException e) {
                log.info("Error : {}",e);
            }
        }

        return list;
    }





}
