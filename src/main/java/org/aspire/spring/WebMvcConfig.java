package org.aspire.spring;

import org.aspire.interceptor.AuthenticationInterceptor;
import org.aspire.interceptor.AuthorizationInterceptor;
import org.aspire.model.RequestMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthorizationInterceptor authorizationInterceptor;


    @Autowired
    public WebMvcConfig(AuthenticationInterceptor authenticationInterceptor, AuthorizationInterceptor authorizationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.authorizationInterceptor = authorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).excludePathPatterns("/auth/**");
        registry.addInterceptor(authorizationInterceptor).excludePathPatterns("/auth/**");
    }

    @Bean
    @RequestScope
    public RequestMetadata getRequestMetadata() {
        return new RequestMetadata();
    }
}
