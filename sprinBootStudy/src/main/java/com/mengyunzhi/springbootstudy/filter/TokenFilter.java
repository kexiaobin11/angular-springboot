package com.mengyunzhi.springbootstudy.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

@WebFilter
public class TokenFilter extends HttpFilter {
    private final static Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    public static String TOKEN_KEY = "auth-token";
    private HashSet<String> tokens = new HashSet<>();
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(TOKEN_KEY);
        logger.info("获取到的的值为" + token);
        if (!this.validateToken(token)) {
            // 如果无效则分发送的token
            token = this.makeToken();
            logger.info("原token无效，发布的新的token为" + token);
        }
        // 在确立响应信息前，设置响应的header值
        response.setHeader(TOKEN_KEY, token);
        chain.doFilter(request, response);
    }
    private boolean validateToken (String token) {
         if (token ==  null) {
             return false;
         } else {
             return this.tokens.contains(token);
         }
    }

    private String makeToken() {
        String token = UUID.randomUUID().toString();
        this.tokens.add(token);
        return token;
    }
}
