package com.sungchul.stock.config.jwt.controller;

import com.sungchul.stock.config.jwt.vo.JwtRequest;
import com.sungchul.stock.config.jwt.vo.JwtResponse;
import com.sungchul.stock.config.jwt.util.JwtTokenUtil;
import com.sungchul.stock.config.jwt.config.JwtUserDetailsService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//JWT 토큰을 발급하는 컨트롤러
//DB에 저장된 값을 가져와서 저장함
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST )
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUserId(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUserId());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
    
    //swagger-ui에서 사용하려고 햇는데 잘안댐
    @RequestMapping(value = "/authenticateGet", method = RequestMethod.GET )
    public ResponseEntity<?> createAuthenticationTokenGet(HttpServletRequest request) throws Exception {

        authenticate(request.getParameter("client_id"), request.getParameter("client_secret"));

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(request.getParameter("client_id"));

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
    
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}