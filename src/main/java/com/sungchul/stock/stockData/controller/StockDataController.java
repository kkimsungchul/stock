package com.sungchul.stock.stockData.controller;

import com.sungchul.stock.common.ResponseAPI;
import com.sungchul.stock.parsing.vo.ParsingVO;
import com.sungchul.stock.stockData.vo.StockVO;
import com.sungchul.stock.stockData.service.StockDataService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/stockData")
public class StockDataController {

    StockDataService stockDataService;

    @GetMapping("/{stockCode}")
    @ApiOperation(
            httpMethod = "GET",
            response = ResponseAPI.class,
            value="해당 주식종목의 정보를 가져옴" ,
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
            notes="해당 API 호출시 데이터베이스에서저장되어 있는 전체 주식종목코드를 리턴해줌")
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
}
