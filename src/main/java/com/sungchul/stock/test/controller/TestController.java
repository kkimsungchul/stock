package com.sungchul.stock.test.controller;

import com.sungchul.stock.common.ResponseAPI;
import com.sungchul.stock.csv.service.CSVService;
import com.sungchul.stock.parsing.Service.ParsingService;
import com.sungchul.stock.parsing.vo.ParsingVO;
import com.sungchul.stock.stockData.vo.StockVO;
import com.sungchul.stock.test.service.TestService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    ParsingService parsingService;
    @Autowired
    CSVService csvService;

    @Autowired
    TestService testService;

    @Value("${test.testword1}")
    private String aaa;

    @GetMapping("")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<LinkedHashMap<String,String>> test(@PathVariable("stockCode") String stockCode)throws Exception{
        LinkedHashMap<String,String> hashMap = parsingService.test(stockCode);
        return new ResponseEntity<>(hashMap,HttpStatus.OK);
    }

    @PostMapping("/test2")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<ResponseAPI> test2(@RequestBody ParsingVO parsingVO )throws Exception{
        LinkedHashMap<String,String> hashMap = parsingService.test(parsingVO.getStockCode());
        HashMap<String,Object> map = new HashMap<>();
        map.put("parsingData",parsingVO);
        ResponseAPI responseAPI = new ResponseAPI();
        responseAPI.setData(map);
        return new ResponseEntity<>(responseAPI,HttpStatus.OK);
    }


    @GetMapping("/test4")
    public void test5(){
        System.out.println("### test4####");
        parsingService.mapperTest();
    }

    @GetMapping("/test6")
    public void test6(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        parsingService.mapperTest();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());

    }
    @PostMapping("/mapperTest2")
    public List<ParsingVO> mapperTest2(@RequestBody StockVO stockVO){
        log.info("### stockVO : {}" , stockVO);
        System.out.println("### aaa : "+aaa);
        return parsingService.mapperTest2(stockVO);


    }
    @GetMapping("/getTest")
    public String getTest(){
        System.out.println("### aaa : "+aaa);
        return "hihihi";
    }






}
