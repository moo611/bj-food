package com.example.bjfood.config.auth;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7); // 获取 "Bearer " 后的 token 部分

                username = jwtUtil.extractUsername(jwt); // 从 JWT 中提取用户名

            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }else {
                    // Token 无效时抛出异常
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"code\":401,\"message\":\"invalid token\"}");
                    return;
                    
                }
            }
        }catch (ExpiredJwtException e) {
            // JWT 过期异常处理
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"code\":401,\"message\":\"token expired\"}");
            return;
            
        } catch (Exception e) {
            // 其他 JWT 解析异常处理
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"code\":401,\"message\":\"invalid token\"}");
            return;
            
        }

        chain.doFilter(request, response);
    }
}
