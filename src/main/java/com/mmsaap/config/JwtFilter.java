package com.mmsaap.config;

import com.mmsaap.entity.User;
import com.mmsaap.repository.UserRepository;
import com.mmsaap.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JWTService jwtService,
                     UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(token != null&&token.startsWith("Bearer ")) {
            String jwtToken = token.substring(8,token.length() - 1);
            System.out.println(jwtToken);
            String username = jwtService.getUsername(jwtToken);
            Optional<User> opUsername = userRepository.findByUsername(username);
            if (opUsername.isPresent()) {
                User user = opUsername.get();
                UsernamePasswordAuthenticationToken
                        userToken =
                        new UsernamePasswordAuthenticationToken(user.getUsername(), null, null);
                userToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(userToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
