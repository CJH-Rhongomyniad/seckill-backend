package com.example.backend.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registerMyFilter(){
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>();
        bean.setOrder(1);
        bean.setFilter(new JwtFilter());
        bean.addUrlPatterns("/api/user/*");
        return bean;
    }
}