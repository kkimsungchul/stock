package com.sungchul.stock.parsing;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Data
@Builder
@NoArgsConstructor //매게변수 없는 생성자
@AllArgsConstructor //매게변수 있는 생성자
@ApiModel("parsing VO")
public class ParsingVO {

    @ApiModelProperty(required = true, name = "stock_code", value = "주식종목코드", notes = "필수", example = "005930")
    @JsonProperty("stock_code")
    private String stockCode;

    @ApiModelProperty(name = "parsing_date_detail", value = "상세 기준 날짜", notes = "필수", example = "2021-11-05 21:50:40")
    @JsonProperty("parsing_date_detail")
    private String parsingDateDetail;

    @ApiModelProperty(name = "parsing_date", value = "기준 날짜", notes = "필수", example = "11/05",reference = "이거뭐임")
    @JsonProperty("parsing_date")
    private String parsingDate;

    @ApiModelProperty(name = "stock_name", value = "종목명", notes = "필수", example = "005930")
    @JsonProperty("stock_name")
    private String stockName;

    @ApiModelProperty(name = "stock_category_code", value = "종목구분코드", notes = "필수", example = "1")
    @JsonProperty("stock_category_code")
    private int stockCategoryCode;

    @ApiModelProperty(name = "stock_category_name", value = "종목구부명", notes = "필수", example = "코스피")
    @JsonProperty("stock_category_name")
    private String stockCategoryName;

    @ApiModelProperty(name = "present_price", value = "현재가", notes = "필수", example = "005930")
    @JsonProperty("present_price")
    private int presentPrice;

    @ApiModelProperty(name = "yesterday_price", value = "전일가", notes = "필수", example = "005930")
    @JsonProperty("yesterday_price")
    private int yesterdayPrice;

    @ApiModelProperty(name = "current_price", value = "시가", notes = "필수", example = "005930")
    @JsonProperty("current_price")
    private int currentPrice;

    @ApiModelProperty(name = "high_price", value = "고가", notes = "필수", example = "005930")
    @JsonProperty("high_price")
    private int highPrice;

    @ApiModelProperty(name = "upper_limit_price", value = "상한가", notes = "필수", example = "005930")
    @JsonProperty("upper_limit_price")
    private int upperLimitPrice;

    @ApiModelProperty(name = "low_price", value = "저가", notes = "필수", example = "005930")
    @JsonProperty("low_price")
    private int lowPrice;

    @ApiModelProperty(name = "lower_limit_price", value = "하한가", notes = "필수", example = "005930")
    @JsonProperty("lower_limit_price")
    private int lowerLimitPrice;

    @ApiModelProperty(name = "trading_volume", value = "거래량", notes = "필수", example = "005930")
    @JsonProperty("trading_volume")
    private int tradingVolume;

    @ApiModelProperty(name = "trading_value", value = "거래대금", notes = "필수", example = "005930")
    @JsonProperty("trading_value")
    private String tradingValue;

    @ApiModelProperty(name = "direction", value = "방향", notes = "필수", example = "005930")
    @JsonProperty("direction")
    private String direction;
    @ApiModelProperty(name = "direction_code", value = "방향코드", notes = "필수", example = "005930")
    @JsonProperty("direction_code")
    private int directionCode;

    @ApiModelProperty(name = "price_gap", value = "가격차", notes = "필수", example = "005930")
    @JsonProperty("price_gap")
    private int priceGap;

    @ApiModelProperty(name = "foreign_trade", value = "외국인 매매", notes = "필수", example = "005930")
    @JsonProperty("foreign_trade")
    private int foreignTrade;

    @ApiModelProperty(name = "institution_trade", value = "기관 매매", notes = "필수", example = "005930")
    @JsonProperty("institution_trade")
    private int institutionTrade;

    @ApiModelProperty(name = "parsing_memo", value = "메모", notes = "오류내용등을 메모", example = "005930")
    @JsonProperty("parsing_memo")
    private  String parsingMemo;

}
