package com.psevdo00.RestAPiICallboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
@EnableJdbcHttpSession
public class SessionConfig {

    @Bean
    public CookieSerializer cookieSerializer(){

        DefaultCookieSerializer serializer = new DefaultCookieSerializer();

        serializer.setCookieName("JSESSIONID");
        serializer.setCookiePath("/");

        return serializer;

    }

}
