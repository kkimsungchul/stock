package com.sungchul.stock.user.controller;


import com.sungchul.stock.common.ResponseAPI;
import com.sungchul.stock.user.service.UserService;
import com.sungchul.stock.user.vo.UserVO;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
public class UserController {

    UserService userService;

    @PostMapping("/user")
    @ApiOperation(
            httpMethod = "POST",
            response = ResponseAPI.class,
            value="회원가입" ,
            notes="해당 API 호출 시 입력한 사용자의 정보로 회원가입")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="roleId" , value = "사용자 권한", defaultValue = "ROLE_ADMIN , ROLE_MANAGER , ROLE_USER")
//    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = Map.class),
            @ApiResponse(code = 403, message = "접근거부", response = HttpClientErrorException.Forbidden.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ServerError.class),
    })
    public ResponseEntity<ResponseAPI> insertUsert(@RequestBody UserVO userVO){
        HashMap<String,Object> hashMap = new HashMap<>();
        ResponseAPI responseAPI = new ResponseAPI();
        if(userService.insertUser(userVO)==1){
            hashMap.put("UserVO" ,userVO);
            hashMap.put("insert status" , "success");
        }else{
            hashMap.put("insert status" , "fail");
        }
        responseAPI.setData(hashMap);
        return new ResponseEntity<>(responseAPI, HttpStatus.OK);
    }

}
