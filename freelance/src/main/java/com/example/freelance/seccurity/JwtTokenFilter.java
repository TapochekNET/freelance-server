package com.example.freelance.seccurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if(token !=null && jwtTokenProvider.validateToken(token)){
                Authentication authentication = jwtTokenProvider.getAuth(token);
                if(authentication != null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (JwtAuthException e){
            ((HttpServletResponse) servletResponse).sendError(401, "JWT token is expired or invalid");
            throw new  JwtAuthException("JWT token is expired or invalid");

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
