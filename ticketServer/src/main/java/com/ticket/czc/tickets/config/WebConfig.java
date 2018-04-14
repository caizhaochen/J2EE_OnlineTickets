package com.ticket.czc.tickets.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    private ApplicationContext applicationContext;

    public WebConfig(){
        super();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("1");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");

        super.addResourceHandlers(registry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("11");
        this.applicationContext = applicationContext;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("111");
        //拦截规则：除了login，其他都拦截判断
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/user/**").excludePathPatterns("/user/login/**","/user/register/**","/user/welcome","/user/activatemail/**");
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/coupon/**");
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/pages/users/**").excludePathPatterns("/pages/users/login.html","/pages/users/register.html");
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/tickets/**").excludePathPatterns("/tickets/venueHome");
//        registry.addInterceptor(new VenueLoginInterceptor()).addPathPatterns("/venue/**").excludePathPatterns("/venue/login/**","/venue/register/**");
        super.addInterceptors(registry);
    }

}
