package com.sungchul.stock.mainController;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/b")
    @ApiOperation(value="메인페이지" , notes="접속시 hi라는 단어를 리턴합니다.")
    public String hi(){
        return "hi";
    }

    @GetMapping("/a")
    @ApiOperation(value="a페이지" , notes="접속시 bye라는 단어를 리턴합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name="a" , value = "걍 테스트용 a변수에 대한 설명", defaultValue = "디폴트값")
    })
    

    public String bye(String a){
        return "bye";
    }
}
