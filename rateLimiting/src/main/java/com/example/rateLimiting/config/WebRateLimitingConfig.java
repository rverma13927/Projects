package com.example.rateLimiting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebRateLimitingConfig implements WebMvcConfigurer {

    @Autowired
    RateLimitingHandler rateLimitingHandler;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(rateLimitingHandler).addPathPatterns("/api/*");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
