package com.sungchul.stock.stockData.service;

import com.sungchul.stock.parsing.vo.ParsingVO;
import com.sungchul.stock.stockData.vo.SearchParamVO;
import com.sungchul.stock.stockData.vo.SearchVO;
import com.sungchul.stock.stockData.vo.StockVO;
import com.sungchul.stock.stockData.mapper.StockDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Service("stockDataService")
public class StockDataService {

    StockDataMapper stockDataMapper;

    /**
     * 데이터베이스에 저장되어 있는 주식가격 정보를 리턴
     * @param stockCode
     * @return List<ParsingVO>
     * */
    public List<ParsingVO> getStockData(String stockCode){

        return stockDataMapper.getStockData(stockCode);
    }

    /**
     * 데이터베이스에 저장되어 있는 주싱코드 정보를 리턴
     * @param
     * @return List<ParsingVO>
     * */
    public List<StockVO> getStockCode(){

        return stockDataMapper.getStockCode();
    }

    /**
     * 주식종목명으로 해당 주식종목코드를 가져옴
     * @param
     * @return List<ParsingVO>
     * */
    public List<HashMap<String,String>> getSearchStockName(String stockName){
        return stockDataMapper.getSearchStockName(stockName);
    }

    /**
     * 입력한 주식 정보로 해당 주식 코드를 가져옴
     * @param stockVO
     * @return List<ParsingVO>
     * */
    public List<ParsingVO> getSearchStock(StockVO stockVO){
        return stockDataMapper.getSearchStock(stockVO);
    }

    /**
     * 입력한 정보로 상승중인 주식과 하락중인 주식을 가져옴
     * @param searchVO
     * @return List<ParsingVO>
     * */
    public List<SearchParamVO> stockFlow(SearchVO searchVO){
        return stockDataMapper.stockFlow(searchVO);
    }






}
