package com.sungchul.stock.user.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("user VO")
public class UserVO {

    @ApiModelProperty(name = "user_id", value = "관리자ID", notes = "필수", example = "")
    @JsonProperty("user_id")
    private String userId;

    @ApiModelProperty(name = "password", value = "비밀번호", notes = "필수", example = "")
    @JsonProperty("password")
    private String password;

    @ApiModelProperty(name = "name", value = "이름", notes = "필수", example = "")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(name = "reg_date", value = "등록일", notes = "필수" )
    @JsonProperty("reg_date")
    private String regDate;

    @ApiModelProperty(name = "reg_time", value = "등록시간", notes = "필수")
    @JsonProperty("reg_time")
    private String regTime;

    @ApiModelProperty(name = "role_id", value = "관리자구분", notes = "필수", example = "ROLE_ADMIN , ROLE_MANAGER , ROLE_USER")
    @JsonProperty("role_id")
    private String roleId;

    @ApiModelProperty(name = "status", value = "상태", notes = "필수", example = "1 : 사용  , 2: 정지")
    @JsonProperty("status")
    private int status;

    @ApiModelProperty(name = "otp", value = "OTP발행여부", notes = "필수")
    @JsonProperty("otp")
    private int otp;

    @ApiModelProperty(name = "otp_key", value = "OTP key", notes = "필수")
    @JsonProperty("otp_key")
    private String otpKey;

}

