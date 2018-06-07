package com.ticket.czc.tickets.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
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

    // 获取配置文件中图片的路径
    @Value("${web.upload-path}")
    private String mImagesPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("1");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");

//        super.addResourceHandlers(registry);

        // 访问图片方法
//        if (mImagesPath.equals("") || mImagesPath.equals("${web.upload-path}")) {
//            String imagesPath = WebConfig.class.getClassLoader().getResource("").getPath();
//            if (imagesPath.indexOf(".jar") > 0) {
//                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
//            } else if (imagesPath.indexOf("classes") > 0) {
//                imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
//            }
//            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
//            mImagesPath = imagesPath;
//        }
//        LoggerFactory.getLogger(WebConfig.class).info("imagesPath=" + mImagesPath);
        registry.addResourceHandler("/showImages/**").addResourceLocations(mImagesPath);


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
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/user/**").excludePathPatterns("/user/login/**","/user/register/**","/user/welcome","/user/activatemail/**","/user/getCheckCode/**");
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/coupon/**");
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/pages/users/**").excludePathPatterns("/pages/users/login.html","/pages/users/register.html");
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/tickets/**").excludePathPatterns("/tickets/venueHome");
//        registry.addInterceptor(new VenueLoginInterceptor()).addPathPatterns("/venue/**").excludePathPatterns("/venue/login/**","/venue/register/**");
        super.addInterceptors(registry);
    }




//    // 访问图片方法
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if (mImagesPath.equals("") || mImagesPath.equals("${web.upload-path}")) {
//            String imagesPath = WebConfig.class.getClassLoader().getResource("").getPath();
//            if (imagesPath.indexOf(".jar") > 0) {
//                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
//            } else if (imagesPath.indexOf("classes") > 0) {
//                imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
//            }
//            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
//            mImagesPath = imagesPath;
//        }
//        LoggerFactory.getLogger(WebConfig.class).info("imagesPath=" + mImagesPath);
//        registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
//        super.addResourceHandlers(registry);
//    }

}
