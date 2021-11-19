package com.sungchul.stock.config.security;

import com.sungchul.stock.user.vo.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserContext extends User {

    private final UserVO userVO;

    public UserContext(UserVO userVO, Collection<? extends GrantedAuthority> authorities) {
        super(userVO.getName(), userVO.getPassword(), authorities);
        this.userVO = userVO;
    }

    public UserVO getUserVO() {
        return userVO;
    }
}
