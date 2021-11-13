package com.sungchul.stock.stockData.vo;




import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("stock VO")
public class StockVO {
    //Gson을 이용할 때에는 @SerializedName을 사용해야하고 jackson을 사용할때에는 @JsonProperty를 사용해야 한다는데
    //https://blog.hodory.dev/2019/06/04/json-property-not-working/
    @ApiModelProperty(name = "seq", value = "생성순서", notes = "필수", example = "005930")
    @JsonProperty("seq")
    private int seq;

    @ApiModelProperty(name = "stock_code", value = "주식 단축 코드", notes = "필수", example = "005930")
    @JsonProperty("stock_code")
    private String stockCode;

    @ApiModelProperty(name = "stock_long_code", value = "주식 표준 코드", notes = "필수", example = "KR7005930003")
    @JsonProperty("stock_long_code")
    private String stockLongCode;

    @ApiModelProperty(name = "stock_name", value = "주식명", notes = "필수", example = "삼성전자")
    @JsonProperty("stock_name")
    private String stockName;

    @ApiModelProperty(name = "stock_category_code", value = "주식 구분 코드, 1: 코스피 , 2: 코스닥 , 3: 코넥스", notes = "필수", example = "1")
    @JsonProperty("stock_category_code")
    private int stockCategoryCode;

    @ApiModelProperty(name = "stock_category_name", value = "주식 구분 명", notes = "필수", example = "코스피")
    @JsonProperty("stock_category_name")
    private String stockCategoryName;

    @ApiModelProperty(name = "insert_date", value = "입력날짜", notes = "필수", example = "20211107195122")
    @JsonProperty("insert_date")
    private String insertDate;

    @ApiModelProperty(name = "stock_category_name", value = "수정날짜", notes = "필수", example = "20211107195122")
    @JsonProperty("update_date")
    private String updateDate;

}
