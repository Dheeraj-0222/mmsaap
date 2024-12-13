package com.mmsaap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SequrityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain
            (HttpSecurity http
    ) throws Exception
    {
        //h(cd)2
        http.csrf().disable().cors().disable();
        //haap
        http.authorizeHttpRequests().anyRequest().permitAll();
        return http.build();
    }
}
