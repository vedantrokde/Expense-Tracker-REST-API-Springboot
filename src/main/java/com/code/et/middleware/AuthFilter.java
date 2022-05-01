package com.code.et.middleware;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthFilter extends GenericFilterBean {
    
    private String apiKey;

    public AuthFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer ");
            if (authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];
                try {
                    Claims claims = Jwts.parser()
                            .setSigningKey(apiKey)
                            .parseClaimsJws(token)
                            .getBody();
                    httpRequest.setAttribute("userId", Integer.parseInt(claims.get("id").toString()));
                } catch (Exception e) {
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization Token is invalid/expired.");
                    return;
                }
            } else {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization Token must be Bearer [token]");
                return;
            }
        } else {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization Token must be provided");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
