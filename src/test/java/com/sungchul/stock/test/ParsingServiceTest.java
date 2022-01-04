//package com.sungchul.stock.test;
//
//import com.sungchul.stock.parsing.Service.ParsingService;
//
//
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//
//@WebMvcTest(ParsingService.class)
//public class ParsingServiceTest {
//
//    @Autowired
//    MockMvc mvc;
//
//    @MockBean
//    ParsingService parsingService;
//
//    @Test
//    @DisplayName("파싱테스트")
//    void parsingTest(){
//        given(parsingService.aa()).willReturn("zzzz");
//        System.out.println(parsingService.aa());
//    }
//}
