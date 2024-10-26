package com.cafe.cafe_management_backend.JWT;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final List<String> allowedEndPoints = Arrays.asList("/user/login","/user/forgotPassword","/user/signup");

    @Autowired
    JwtUtil jwtService;
    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    String email = null;
    Claims claims = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;

        if(allowedEndPoints.contains(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring((7));//7 = "Bearer "
            email = jwtService.extractUsername(token);
            claims = jwtService.extractAllClaims(token);
        }
        if(email!= null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails customerDetail = customerUsersDetailsService.loadUserByUsername(email);

            System.out.println("In doFillter"+jwtService.validateToken(token,customerDetail));//check have some bug becase this false
            System.out.println("In doFillter"+token);

            //Have some bug after this line
            if(jwtService.validateToken(token,customerDetail)){

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customerDetail,null,
                        customerDetail.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }

//    public String getEmail(){
//        return email;
//    }

    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }
    public boolean isUser(){
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }
    public String getCurrentUser(){
        return email;
    }
}

