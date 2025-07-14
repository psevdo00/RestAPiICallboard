package com.psevdo00.RestAPiICallboard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psevdo00.RestAPiICallboard.dto.response.UserResponse;
import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.service.CustomUserDetailsService;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception{

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                        .requestMatchers("/index.html").permitAll()
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
                        .loginProcessingUrl("/login")
                        .successHandler(customAuthSuccessHandler())
                        .permitAll()
                )
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthSuccessHandler(){

        return ((request, response, authentication) -> {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            UserEntity user = (UserEntity) authentication.getPrincipal();

            new ObjectMapper().writeValue(response.getWriter(), convertor(user));

        });

    }

    private UserResponse convertor(UserEntity user){

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setFamilyName(user.getFamilyName());
        response.setMiddleName(user.getMiddleName());
        response.setEmail(user.getEmail());
        response.setNumPhone(user.getNumPhone());
        response.setRole(user.getRole().toString());

        return response;

    }

}
