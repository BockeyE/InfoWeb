package com.InfoWeb.demo.config;


import com.InfoWeb.demo.interceptor.LoginRequiredInterceptor;
import com.InfoWeb.demo.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor)
                .addPathPatterns("/hello/*")
                .addPathPatterns("/like")
                .addPathPatterns("/dislike");
        super.addInterceptors(registry);
    }
}
