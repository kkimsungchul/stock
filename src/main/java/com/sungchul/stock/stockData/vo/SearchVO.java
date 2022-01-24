package com.sungchul.stock.stockData.vo;




import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("Search VO")
public class SearchVO {


    @ApiModelProperty(name = "workingDay", value = "조회할 워킹데이 일수 ", notes = "ex) 10 일경우 10일동안의 정보를 조회", example = "5")
    @JsonProperty("working_Day")
    private int workingDay;

    @ApiModelProperty(name = "directionCode", value = "방향코드, 1: 상향 , 2 :하향 , 3 : 보합", notes = "필수", example = "1")
    @JsonProperty("direction_code")
    private int directionCode;

}
