package com.example.bjfood.config.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 自定义返回 401 状态码和错误信息
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"code\":401,\"message\":\"invalid token\"}");
    }
}
