package com.sungchul.stock.stockData.controller;

import com.sungchul.stock.common.ResponseAPI;
import com.sungchul.stock.parsing.vo.ParsingVO;
import com.sungchul.stock.stockData.vo.SearchParamVO;
import com.sungchul.stock.stockData.vo.SearchVO;
import com.sungchul.stock.stockData.vo.StockVO;
import com.sungchul.stock.stockData.service.StockDataService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@AllArgsConstructor
@RestController
@Api(tags = {"DB에 저장되어 있는 주식 파싱 데이터를 제공해주는 Controller"})
@RequestMapping("/stockData")
public class StockDataController {

    StockDataService stockDataService;

    @GetMapping("/{stockCode}")
    @ApiOperation(
            httpMethod = "GET",
            response = ResponseAPI.class,
            value="DB에 저장되어 있는 해당 주식종목의 정보를 가져옴" ,
            notes="해당 API 호출시 데이터베이스에서 입력한 주식종목코드에 대한 파싱해둔 데이터를 리턴해줌")
    @ApiImplicitParams({
            @ApiImplicitParam(name="stockCode" , value = "주식 종목 코드", defaultValue = "005930")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<ResponseAPI> getStockData(@PathVariable("stockCode") String stockCode){
        HashMap<String,Object> hashMap = new HashMap<>();
        ResponseAPI responseAPI = new ResponseAPI();
        List<ParsingVO> stockDataList = stockDataService.getStockData(stockCode);
        hashMap.put("stockDataList",stockDataList);

        responseAPI.setData(hashMap);
        return new ResponseEntity<>(responseAPI, HttpStatus.OK);
    }

    @GetMapping("/stockCode")
    @ApiOperation(
            httpMethod = "GET",
            response = ResponseAPI.class,
            value="전체 주식종목 코드를 가져옴" ,
            notes="해당 API 호출시 데이터베이스에서저장되어 있는 전체 주식종목코드를 리턴해줌")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<ResponseAPI> getStockCode(){
        HashMap<String,Object> hashMap = new HashMap<>();
        ResponseAPI responseAPI = new ResponseAPI();
        List<StockVO> stockCodeList = stockDataService.getStockCode();
        hashMap.put("stockCodeList",stockCodeList);

        responseAPI.setData(hashMap);
        return new ResponseEntity<>(responseAPI, HttpStatus.OK);
    }

    @GetMapping("/stockCode/{stockName}")
    @ApiOperation(
            httpMethod = "GET",
            response = ResponseAPI.class,
            value="주싱명을 검색하여 주식종목 코드를 가져옴" ,
            notes="해당 API 호출시 데이터베이스에서 이름조건에 맞는 주식 파싱 데이터를 리턴해줌")
    @ApiImplicitParams({
            @ApiImplicitParam(name="stockName" , value = "주식 종목 이름", defaultValue = "삼성")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<ResponseAPI> getSearchStockName(@PathVariable("stockName") String stockName){
        HashMap<String,Object> hashMap = new HashMap<>();
        ResponseAPI responseAPI = new ResponseAPI();
        List<HashMap<String,String>> searchStockList = stockDataService.getSearchStockName(stockName);
        hashMap.put("searchStockList",searchStockList);

        responseAPI.setData(hashMap);
        return new ResponseEntity<>(responseAPI, HttpStatus.OK);
    }

    @PostMapping("/stockData")
    @ApiOperation(
            httpMethod = "POST",
            response = ResponseAPI.class,
            value="주식정보로 검색하여 데이터를 가져옴" ,
            notes="해당 API 호출시 데이터베이스에서 검색조건에 맞는 주식 파싱 데이터를 리턴해줌")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="stock_category_name" , value = "주식 구분 명", defaultValue = "코스피"),
//            @ApiImplicitParam(name="stock_category_code" , value = "주식 구분 코드, 1: 코스피 , 2: 코스닥 , 3: 코넥스", defaultValue = "1"),
//            @ApiImplicitParam(name="stock_name" , value = "주식 종목 이름", defaultValue = "삼성"),
//            @ApiImplicitParam(name="stock_code" , value = "주식 단축 코드", defaultValue = "005930"),
//            @ApiImplicitParam(name="insert_date" , value = "검색날짜", defaultValue = "20211117")
//
//
//    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<ResponseAPI> getSearchStock(@RequestBody StockVO stockVO){
        log.info("### stockVO : {}" , stockVO);
        HashMap<String,Object> hashMap = new HashMap<>();
        ResponseAPI responseAPI = new ResponseAPI();
        List<ParsingVO> searchStockList = stockDataService.getSearchStock(stockVO);
        hashMap.put("searchStockList",searchStockList);
        responseAPI.setData(hashMap);
        return new ResponseEntity<>(responseAPI, HttpStatus.OK);

    }



    @GetMapping("/stockFlow")
    @ApiOperation(
            httpMethod = "GET",
            response = ResponseAPI.class,
            value="상승,하락 구분값과 working day 를 기준으로 상승중인 종목과 하락중인 종목을 가져옴" ,
            notes="상승,하락 구분값과 working day 를 기준으로 상승중인 종목과 하락중인 종목을 가져옴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<ResponseAPI> getStockFlow(SearchVO searchVO){
        System.out.println("### searchVO : " + searchVO);

        HashMap<String,Object> hashMap = new HashMap<>();
        ResponseAPI responseAPI = new ResponseAPI();
        List<SearchParamVO> flowList = stockDataService.stockFlow(searchVO);
        hashMap.put("flowList",flowList);

        responseAPI.setData(hashMap);
        return new ResponseEntity<>(responseAPI, HttpStatus.OK);
    }
}
