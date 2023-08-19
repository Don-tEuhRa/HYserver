package com.example.reborn.config;


import com.example.reborn.utils.XssCharacterEscapes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebConfigure implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("/*")    //외부에서 들어오는 모둔 url 을 허용
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")    //허용되는 Method
                .allowedHeaders("*")    //허용되는 헤더
                .allowCredentials(true)    //자격증명 허용
                .maxAge(3600);   //허용 시간
    }

    @Value("${resource.file.path}")
    private String filePath;

    @Value("${resource.file.url}")
    private String fileURL;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("Resource File Mapped : {} -> {}", fileURL, filePath);
        registry
                .addResourceHandler(fileURL + "/**")
                .addResourceLocations("file://" + filePath + File.separator);
    }

    private final ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        final FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.setOrder(1);
        filterRegistration.addUrlPatterns("/*"); //filter를 거칠 url patterns
        return filterRegistration;
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        ObjectMapper copy = objectMapper.copy();
        copy.getFactory().setCharacterEscapes(new XssCharacterEscapes());
        return new MappingJackson2HttpMessageConverter(copy);
    }


}