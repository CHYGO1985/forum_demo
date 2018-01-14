package com.jingjie.forum_demo.configuration;

import com.jingjie.forum_demo.interceptor.TicketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * The class is for adding customised interceptor.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 14, 2018
 */
@Component
public class ForumDemoWebConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    TicketInterceptor ticketInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ticketInterceptor);
        super.addInterceptors(registry);
    }
}
