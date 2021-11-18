package com.sungchul.stock.parsing.controller;


import com.sungchul.stock.common.ResponseAPI;
import com.sungchul.stock.csv.service.CSVService;
import com.sungchul.stock.parsing.Service.ParsingService;
import com.sungchul.stock.parsing.vo.ParsingVO;
import com.sungchul.stock.stockData.vo.StockVO;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@AllArgsConstructor
@RestController
@Api(tags = {"데이터 파싱 서비스를 제공하는 Controller"})
@RequestMapping("/parsing")
public class ParsingController {

    ParsingService parsingService;
    CSVService csvService;

    @GetMapping("")
    @ApiOperation(value="전체 정보 파싱" , notes="해당 API 호출 시 주식 정보를 리턴해줌")
    public ResponseEntity<ResponseAPI> parsingAllStock() throws Exception{
        HashMap<String,Object> hashMap = new HashMap<>();
        ResponseAPI responseAPI = new ResponseAPI();
        List<ParsingVO>  parsingList = parsingService.parsingAllStock();
        hashMap.put("parsingData",parsingList);
        responseAPI.setData(hashMap);
        return new ResponseEntity<>(responseAPI,HttpStatus.OK);
    }

//    @GetMapping("/parsing/{stockCode}")
//    @ApiOperation(value="한 종목 파싱" , notes="해당 API 호출 시 입력한 종목코드에 대한 정보를 리턴")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="stockCode" , value = "주식 종목 코드", defaultValue = "005930")
//    })
//    public ResponseEntity<ParsingVO> parsingOneStock(@PathVariable("stockCode") String stockCode) throws Exception{
//        ParsingVO parsingVO = parsingService.parsingOneStock(stockCode);
//        return new ResponseEntity<>(parsingVO, HttpStatus.OK);
//    }

    @GetMapping("/{stockCode}")
    @ApiOperation(
            httpMethod = "GET",
            response = ResponseAPI.class,
            value="한 종목 파싱" ,
            notes="해당 API 호출 시 입력한 종목코드에 대한 정보를 리턴")
    @ApiImplicitParams({
            @ApiImplicitParam(name="stockCode" , value = "주식 종목 코드", defaultValue = "005930")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<ResponseAPI> parsingOneStock(@PathVariable("stockCode") String stockCode) throws Exception{
        HashMap<String,Object> hashMap = new HashMap<>();
        ResponseAPI responseAPI = new ResponseAPI();
        ParsingVO parsingVO = parsingService.parsingOneStock(stockCode);
        hashMap.put("parsingData",parsingVO);

        responseAPI.setData(hashMap);
        return new ResponseEntity<>(responseAPI,HttpStatus.OK);
    }


    @GetMapping("/saveStockList")
    @ApiOperation(
            httpMethod = "GET",
            value="저장된 CSV 파일의 주싱정보를 DB에 저장" ,
            notes="저장된 CSV 파일의 주싱정보를 DB에 저장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public void saveStockList(){
        csvService.saveStockList();
    }


    @GetMapping("/saveParsingData")
    @ApiOperation(value="주식정보를 파싱하여 DB에 저장" , notes="DB에 저장되어 있는 주식종목 정보를 토대로 주싱정보를 파싱하여 DB에 저장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public int saveParsingData()throws Exception{
        return parsingService.saveParsingData();
    }





    @GetMapping("/csvReadStockList")
    public void csvReadStockList(){
        csvService.readStockList();
    }




}
