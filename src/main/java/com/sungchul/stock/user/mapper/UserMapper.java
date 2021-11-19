package com.sungchul.stock.user.mapper;

import com.sungchul.stock.user.vo.UserVO;

public interface UserMapper {

     UserVO getUser(String userID);

     int insertUser(UserVO userVO);
}
