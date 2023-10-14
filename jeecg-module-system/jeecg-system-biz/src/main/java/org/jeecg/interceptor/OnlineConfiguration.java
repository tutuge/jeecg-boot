package org.jeecg.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration("onlineConfiguration")
public class OnlineConfiguration implements WebMvcConfigurer {
    @Bean
    public a onlineInterceptor() {
        return new a();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        String[] arrayOfString = {"/*.html", "/html/**", "/js/**", "/css/**", "/images/**"};
        registry.addInterceptor(onlineInterceptor()).excludePathPatterns(arrayOfString).addPathPatterns(new String[]{"/online/cgform/api/**"});
    }
}
