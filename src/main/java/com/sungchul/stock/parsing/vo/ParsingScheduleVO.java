package com.sungchul.stock.parsing.vo;


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
@ApiModel("parsingSchedule VO")
public class ParsingScheduleVO {

    @ApiModelProperty(required = true, name = "seq", value = "순번", notes = "필수", example = "005930")
    @JsonProperty("seq")
    private String seq;

    @ApiModelProperty(required = true, name = "start_time", value = "시작시간(yyyyMMddHHmmss)", notes = "필수", example = "005930")
    @JsonProperty("start_time")
    private String startTime;

    @ApiModelProperty(required = true, name = "end_time", value = "종료시간(yyyyMMddHHmmss)", notes = "필수", example = "005930")
    @JsonProperty("end_time")
    private String endTime;

    @ApiModelProperty(required = true, name = "elapsed_time", value = "작업시간 (단위: 밀리초 1초 = 1000)", notes = "필수", example = "005930")
    @JsonProperty("elapsed_time")
    private long elapsedTime;

    @ApiModelProperty(required = true, name = "schedule_date", value = "작업일(yyyyMMdd)", notes = "필수", example = "005930")
    @JsonProperty("schedule_date")
    private String scheduleDate;
}
