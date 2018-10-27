package net.minthe.dbsbookshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Michael Kelley on 10/26/2018
 *
 * Spring configuration class to register LoginInterceptor
 */
@Configuration
public class BookStoreWebMvcConfigurer implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;

    @Autowired
    public BookStoreWebMvcConfigurer(LoginInterceptor loginInterceptor) {this.loginInterceptor = loginInterceptor;}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor);
    }
}

