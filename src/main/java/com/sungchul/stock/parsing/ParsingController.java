package com.sungchul.stock.parsing;


import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@AllArgsConstructor
@RestController
public class ParsingController {

    ParsingService parsingService;


    @GetMapping("/parsing")
    @ApiOperation(value="전체 정보 파싱" , notes="해당 API 호출 시 주식 정보를 리턴해줌")
    public ResponseEntity<List<ParsingVO>> parsingAllStock() throws Exception{
        List<ParsingVO>  parsingList = parsingService.parsingAllStock();
        return new ResponseEntity<>(parsingList, HttpStatus.OK);
    }

    @GetMapping("/parsing/{stockCode}")
    @ApiOperation(value="한 종목 파싱" , notes="해당 API 호출 시 입력한 종목코드에 대한 정보를 리턴")
    @ApiImplicitParams({
            @ApiImplicitParam(name="stockCode" , value = "주식 종목 코드", defaultValue = "005930")
    })
    public ResponseEntity<ParsingVO> parsingOneStock(@PathVariable("stockCode") String stockCode) throws Exception{
        ParsingVO parsingVO = parsingService.parsingOneStock(stockCode);
        return new ResponseEntity<>(parsingVO, HttpStatus.OK);
    }

    @GetMapping("/test")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<ParsingVO> test(ParsingVO parsingVO )throws Exception{
        parsingService.test();
        return new ResponseEntity<>(parsingVO,HttpStatus.OK);
    }


}
