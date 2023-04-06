package com.example.demo.security;


import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.security.util.SecurityConstants;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);


    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


}