package com.sungchul.stock.parsing.mapper;

import com.sungchul.stock.parsing.vo.ParsingScheduleVO;
import com.sungchul.stock.parsing.vo.ParsingVO;
import com.sungchul.stock.parsing.vo.StockVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ParsingMapper {

    List<Map<String,Object>> test();
    List<ParsingVO>getDateTest(StockVO stockVO);

    int insertStockInfo(StockVO stockVO);

    int insertParsingData(ParsingVO parsingVO);

    List<StockVO> getStockList();

    int saveParsingScheduleLog(ParsingScheduleVO parsingScheduleVO);
}
