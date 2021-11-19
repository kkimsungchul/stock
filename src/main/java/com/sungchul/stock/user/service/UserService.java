package com.sungchul.stock.user.service;


import com.sungchul.stock.common.util.DateService;
import com.sungchul.stock.user.mapper.UserMapper;
import com.sungchul.stock.user.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service("userService")
public class UserService {


    UserMapper userMapper;
    DateService dateService;

    public int insertUser(UserVO userVO){

        userVO.setRegDate(dateService.getDate());
        userVO.setRegTime(dateService.getTime());
        userVO.setPassword(new BCryptPasswordEncoder().encode(userVO.getPassword()));

        return userMapper.insertUser(userVO);
    }
}
