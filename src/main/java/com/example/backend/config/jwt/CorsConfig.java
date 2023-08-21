package com.example.backend.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //클라이언트가 자격 증명과 함께 요청을 보내는 것을 허용합니다 (예: 쿠키, HTTP 인증). 자격 증명이 있는 요청에 응답할 때 서버는 이 헤더를 포함해야합니다.
        config.addAllowedOriginPattern("*"); //모든 출처에서의 요청을 허용합니다.
        config.addAllowedHeader("*"); //모든 Header 응답허용
        config.addAllowedMethod("*"); //모든 Http Request Method 응답허용
        config.addExposedHeader("Authorization"); //브라우저가 이 헤더에 접근할 수 있또록 노출. 클라이언트에서 인증 정보를 얻기위해
//        config.addExposedHeader("RefreshToken");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}