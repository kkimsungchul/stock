package com.sungchul.stock.parsing;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor //매게변수 없는 생성자
@AllArgsConstructor //매게변수 있는 생성자
@ApiModel("stock VO")
public class StockVO {

    @ApiModelProperty(required = true, name = "seq", value = "생성순서", notes = "필수", example = "005930")
    @JsonProperty("seq")
    private int seq;

    @ApiModelProperty(name = "stock_code", value = "주식 단축 코드", notes = "필수", example = "2021-11-05 21:50:40")
    @JsonProperty("stock_code")
    private String stockCode;

    @ApiModelProperty(name = "stock_long_code", value = "주식 표준 코드", notes = "필수", example = "2021-11-05 21:50:40")
    @JsonProperty("stock_long_code")
    private String stockLongCode;

    @ApiModelProperty(name = "stock_name", value = "주식명", notes = "필수", example = "2021-11-05 21:50:40")
    @JsonProperty("stock_name")
    private String stockName;

    @ApiModelProperty(name = "stock_category_code", value = "주식 구분 코드, 1: 코스피 , 2: 코스닥 , 3: 코넥스", notes = "필수", example = "2021-11-05 21:50:40")
    @JsonProperty("stock_category_code")
    private int stockCategoryCode;

    @ApiModelProperty(name = "stock_category_name", value = "주식 구분 명", notes = "필수", example = "2021-11-05 21:50:40")
    @JsonProperty("stock_category_name")
    private String stockCategoryName;

    @ApiModelProperty(name = "insert_date", value = "주식 구분 명", notes = "필수", example = "2021-11-05 21:50:40")
    @JsonProperty("insert_date")
    private String insertDate;

    @ApiModelProperty(name = "stock_category_name", value = "주식 구분 명", notes = "필수", example = "2021-11-05 21:50:40")
    @JsonProperty("update_date")
    private String updateDate;
}
