package com.sungchul.stock.parsing;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


@Slf4j
@AllArgsConstructor
@RestController
public class ParsingController {

    ParsingService parsingService;


    @GetMapping("/parsing")
    @ApiOperation(value="전체 정보 파싱" , notes="해당 API 호출 시 주식 정보를 리턴해줌")
    public ResponseEntity<List<LinkedHashMap<String,String>>> parsingAllStock() throws Exception{
        List<LinkedHashMap<String,String>>  parsingList = parsingService.parsingAllStock();
        return new ResponseEntity<>(parsingList, HttpStatus.OK);
    }

    @GetMapping("/parsing/{stockCode}")
    @ApiOperation(value="한 종목 파싱" , notes="해당 API 호출 시 입력한 종목코드에 대한 정보를 리턴")
    @ApiImplicitParams({
            @ApiImplicitParam(name="stockCode" , value = "주식 종목 코드", defaultValue = "005930")
    })
    public ResponseEntity<LinkedHashMap<String,String>> parsingOneStock(@PathVariable("stockCode") String stockCode) throws Exception{
        LinkedHashMap<String,String> parsingMap = parsingService.parsingOneStock(stockCode);
        return new ResponseEntity<>(parsingMap, HttpStatus.OK);
    }

    @GetMapping("/test")
    public void test()throws Exception{
        parsingService.test();
    }


}
