package com.example.backend.config;

import com.example.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = JwtUtil.parseToken(token);
                // 将用户信息存储在请求属性中，便于后续使用
                httpRequest.setAttribute("userId", claims.get("userId"));
                httpRequest.setAttribute("username", claims.getSubject());
            } catch (Exception e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token 无效或已过期");
                return;
            }
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "缺少 Authorization 头");
            return;
        }
        chain.doFilter(request, response);
    }
}
