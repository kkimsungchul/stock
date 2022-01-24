package com.sungchul.stock.stockData.vo;




import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("Search VO")
public class SearchParamVO {


    @ApiModelProperty(name = "stock_name", value = "주식 종목 이름", notes = "", example = "삼성전자")
    @JsonProperty("stock_name")
    private String stockName;

    @ApiModelProperty(name = "flow_cnt", value = "상승 일수", notes = "", example = "1")
    @JsonProperty("flow_cnt")
    private int flowCnt;

}
