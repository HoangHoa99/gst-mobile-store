package com.example.store.security;

import com.example.store.constant.AppConstant;
import com.example.store.dao.custom.CustomUserDetail;
import com.example.store.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    UserService userService;


    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/api/users/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = request.getHeader("x-auth-token");

            Jwts.parser().setSigningKey(AppConstant.JWT_SECRET).parseClaimsJws(jwt);

            Claims claims = Jwts.parser().setSigningKey(AppConstant.JWT_SECRET).parseClaimsJws(jwt).getBody();

            String username = String.valueOf(claims.get("username"));
            String role = String.valueOf(claims.get("role"));


            CustomUserDetail userDetails = (CustomUserDetail) userService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }catch (NullPointerException | NoSuchElementException e){
            resolver.resolveException(request, response, null, new ValidationException("Access denied"));
        }
    }
}
