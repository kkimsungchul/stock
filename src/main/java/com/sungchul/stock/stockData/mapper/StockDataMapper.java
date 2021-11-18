package com.sungchul.stock.stockData.mapper;


import com.sungchul.stock.parsing.vo.ParsingVO;
import com.sungchul.stock.stockData.vo.StockVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface StockDataMapper {


    List<ParsingVO> getStockData(String stockCode);

    List<StockVO> getStockCode();

    List<HashMap<String,String>> getSearchStockName(String stockName);

    List<ParsingVO> getSearchStock(StockVO stockVO);
}
