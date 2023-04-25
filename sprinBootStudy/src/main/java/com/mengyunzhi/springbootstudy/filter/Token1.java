package com.mengyunzhi.springbootstudy.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public class Token1 extends HttpFilter {
    private String AUTH_TOKEN = "auth-token";
    private HashSet<String> tokens = new HashSet<>();
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
     String token = request.getHeader(this.AUTH_TOKEN);
     if (!this.validateToken(token)) {
         token = this.makeKey();
     }
     response.setHeader(AUTH_TOKEN, token);
     chain.doFilter(request, response);
    }

    private boolean validateToken(String token) {
        if (token == null) {
            return false;
        } else {
            return this.tokens.contains(token);
        }
    }
    private String makeKey() {
        String token = UUID.randomUUID().toString();
        this.tokens.add(token);
        return token;
    }
}
