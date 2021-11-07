package com.sungchul.stock.parsing;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ParsingMapper {

    List<Map<String,Object>> test();

    int insertStockInfo(StockVO stockVO);

    int insertParsingData(ParsingVO parsingVO);

    List<StockVO> getStockList();

}
