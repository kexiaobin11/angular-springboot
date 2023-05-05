package com.mengyunzhi.springbootstudy.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

@WebFilter
public class TokenFilter extends HttpFilter {
    private final static Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    public static String TOKEN_KEY = "auth-token";
    /** 存储已分发过的令牌 */
    private HashSet<String> tokens = new HashSet<>();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取 header中的token
        String token = request.getHeader(TOKEN_KEY);
        logger.info("获取到的token为" + token);

        // 有效性判断
        if (!this.validateToken(token)) {
            token = this.makeToken();
            logger.info("原token无效，发布的新的token为" + token);

            // 设置header中的auth-token
            request = new HttpServletRequestTokenWrapper(request, token);
        }

        logger.info("在控制器被调用以前执行");

        response.setHeader(TOKEN_KEY, token);

        chain.doFilter(request, response);

        logger.info("在控制器被调用以后执行");
    }

    /**
     * 生成token
     * 将生成的token存入已分发的tokens中
     * @return token
     */
    private String makeToken() {
        String token = UUID.randomUUID().toString();
        this.tokens.add(token);
        return token;
    }

    private boolean validateToken(String token) {
        if (token == null) {
            return false;
        }

        return this.tokens.contains(token);
    }

    /**
     * 带有请求token的Http请求
     */
    class HttpServletRequestTokenWrapper extends HttpServletRequestWrapper {
        String token;
        private HttpServletRequestTokenWrapper(HttpServletRequest request) {
            super(request);
        }

        public HttpServletRequestTokenWrapper(HttpServletRequest request, String token) {
            this(request);
            this.token = token;
        }

        @Override
        public String getHeader(String name) {
            if (TOKEN_KEY.equals(name)) {
                return this.token;
            }
            return super.getHeader(name);
        }
    }
}

