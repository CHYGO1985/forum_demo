package com.jingjie.forum_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * Controller for the index page.
 *
 * @author jingjiejiang
 * @history
 * 1. Created on Jan 11, 2018.
 *
 */
@Controller
public class HomeController {

    // Define logger object for the class
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping (path = {"/", "/index"}, method = {RequestMethod.GET})
    public String home() {

        return "index";
    }
}
