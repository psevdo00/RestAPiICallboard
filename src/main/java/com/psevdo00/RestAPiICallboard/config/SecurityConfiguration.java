package com.psevdo00.RestAPiICallboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception{

        return http
                .authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                        .requestMatchers("index.html").permitAll()
                        .requestMatchers("/register.html").anonymous()
                        .requestMatchers(HttpMethod.POST,"/api/user/login").anonymous()
                        .requestMatchers(HttpMethod.POST, "/api/user").anonymous()
                        .requestMatchers("/css/**","/js/**", "/img/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/advt**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/category").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .defaultSuccessUrl("/index.html")
                        .permitAll()
                )
                .build();

    }

}
