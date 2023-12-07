package ru.katok.tamctf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

//@Controller
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/about").setViewName("about");
        {
            registry.addViewController("/").setViewName("index");
            registry.addViewController("/index").setViewName("index");
        }
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/news").setViewName("news");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/recovery").setViewName("recovery");
        registry.addViewController("/scoreboard").setViewName("scoreboard");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/tasks").setViewName("tasks");
        registry.addViewController("/teams").setViewName("teams");
        registry.addViewController("/admin").setViewName("admin");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/").setCacheControl(CacheControl.maxAge(Duration.ofDays(365)));
    }
}