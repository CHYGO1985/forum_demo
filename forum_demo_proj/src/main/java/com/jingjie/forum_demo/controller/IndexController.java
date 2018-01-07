package com.jingjie.forum_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Controller of the forum.
 *
 * 1. learn to set path
 * 2. learn to get attributes and parameters from URL path
 * 3. learn to load freemarker template.
 *
 * @author jingjiejiang
 * @history
 * 1. Jan 7, 2018
 */
@Controller
public class IndexController {

    // visit page via /index or /
    @RequestMapping (path = {"/", "/index"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index() {

        return "Hello JJ Forum";
    }

    // visit page via parameters in URL
    // get papameters from request "?type=2&key=aa"
    // http://127.0.0.1:8080/profile/2/2?type=2&key=aaa
    @RequestMapping (path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String checkViaId (@PathVariable("userId") int userId,
                              @PathVariable("groupId") String groupId,
                              @RequestParam(value = "type", defaultValue = "1") int type,
                              @RequestParam(value = "key", defaultValue = "aaa", required =  false) String key) {

        return String.format("The profile of %s and %d with type %d and %s",
                groupId, userId, type, key);

    }

    // loading a freemarker template : .ftl file in /resources/templates
    @RequestMapping (path = {"/template"}, method = RequestMethod.GET)
    public String template (Model model) {
        model.addAttribute("value1", "vvvv");
        return "template";
    }
}
