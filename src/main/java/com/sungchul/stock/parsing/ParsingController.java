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


@Slf4j
@AllArgsConstructor
@RestController
public class ParsingController {

    ParsingService parsingService;


    @GetMapping("/parsing")
    @ApiOperation(value="전체 정보 파싱" , notes="해당 API 호출 시 주식 정보를 리턴해줌")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="a" , value = "걍 테스트용 a변수에 대한 설명", defaultValue = "디폴트값")
//    })
    public ResponseEntity<HashMap<String,Object>> testParsing(){
        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("zzzz","zzzaaaz");


        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @GetMapping("/parsing/{stockCode}")
    @ApiOperation(value="한 종목 파싱" , notes="해당 API 호출 시 입력한 종목코드에 대한 정보를 리턴")
    @ApiImplicitParams({
            @ApiImplicitParam(name="stockCode" , value = "주식 종목 코드", defaultValue = "005930")
    })
    public ResponseEntity<HashMap<String,String>> testParsing(@PathVariable("stockCode") String stockCode) throws Exception{
        HashMap<String,String> hashMap = parsingService.parsingOneStock(stockCode);
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

}
