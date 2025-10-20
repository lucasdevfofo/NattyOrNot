package com.trabalho.NattyOrNot.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("${security.api-key.header}")
    private String headerName;

    @Value("${security.api-key.public-paths:}")
    private String publicPathsCsv;

    @Bean
    public FilterRegistrationBean<ApiKeyAuthFilter> apiKeyAuthFilter(ApiKeyService service) {
        List<String> publicPaths = publicPathsCsv == null || publicPathsCsv.isBlank()
                ? List.of()
                : Arrays.asList(publicPathsCsv.split(","));

        var reg = new FilterRegistrationBean<ApiKeyAuthFilter>();
        reg.setFilter(new ApiKeyAuthFilter(service, headerName, publicPaths));
        reg.setOrder(1); // roda cedo
        return reg;
    }
}
