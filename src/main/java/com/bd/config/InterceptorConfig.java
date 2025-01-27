package com.bd.config;

import com.bd.interceptor.BDInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 夕雾
 * @description: TODO
 * @date 2024/9/24 20:14
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BDInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**");
    }
}
