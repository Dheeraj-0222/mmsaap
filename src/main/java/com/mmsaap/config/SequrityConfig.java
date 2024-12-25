package com.mmsaap.config;

import com.mmsaap.service.JWTService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SequrityConfig {
    private JwtFilter jwtFilter;

    public SequrityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain
            (HttpSecurity http
    ) throws Exception
    {
        //h(cd)2
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        //haap
        http.authorizeHttpRequests().anyRequest().permitAll();


        /*http.authorizeHttpRequests()
                .requestMatchers("/api/auth/sign-up","/api/auth/login","/api/auth/property/sign-up")
                .permitAll()
                .requestMatchers("api/v1/property/addProperty")
                .hasRole("OWNER")
                .requestMatchers("api/v1/property/deleteProperty")
                .hasAnyRole("OWNER","ADMIN")
                .requestMatchers("/api/auth/blog/sign-up")
                .hasRole("ADMIN")
                .anyRequest().authenticated();
        ;*/

        /*http.authorizeHttpRequests()
                .requestMatchers("/api/auth/sign-up","/api/auth/login")
                .permitAll()
                .requestMatchers("/api/v1/property/addProperty")
                .hasRole("PROPERTY_OWNER")
                .anyRequest().authenticated();*/
        return http.build();
    }
}
