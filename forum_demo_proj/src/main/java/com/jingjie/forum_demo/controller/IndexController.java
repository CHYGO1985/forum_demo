package com.jingjie.forum_demo.controller;

import com.jingjie.forum_demo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

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

        List<String> colors = Arrays.asList(new String[] {"RED", "YELLOW", "BLUE"});

        model.addAttribute("colors", colors);

        Map<Integer, Integer> squares = new HashMap<>();
        for (int i = 0; i < 2; i ++) {
            squares.put(i + 1, (i + 1) * (i + 1));
        }
        model.addAttribute("squares", squares);

        // use customised java class
        User user = new User();
        user.setName("Damn");
        model.addAttribute(user);


       return "template";
    }

    // get info regardign header and cookies
    // two ways of getting cookies : 1) from HttpServletRequest 2) from @CookieValue
    // response -->  HttpServletResponse, request --> HttpServletRequest
    @RequestMapping (path = {"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId) {

        StringBuilder builder = new StringBuilder();
        builder.append("COOKIE_VALUE: " + sessionId);
        builder.append(request.getMethod() + "<br>");
        builder.append(request.getQueryString() + "<br>");
        builder.append(request.getPathInfo() + "<br>");

        Enumeration<String> headerName = request.getHeaderNames();

        while (headerName.hasMoreElements()) {
            String name = headerName.nextElement();
            builder.append(name + ": " + request.getHeader(name) + "<br>");
        }

        if (request.getCookies() != null) {
            for(Cookie cookie : request.getCookies()) {
                builder.append("Cookie: " + cookie.getName() + " value: " + cookie.getValue());
            }
        }

        return builder.toString();
    }


    // use RedirectView to redirect a visit (used to switch between sites for mobile and PC)
    @RequestMapping (path = {"/redirect/{code}"}, method = {RequestMethod.GET})
    public RedirectView redirect (@PathVariable("code") int code){

        RedirectView redir = new RedirectView("/", true);
        if (code == 301) {
            redir.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }

        return redir;
    }

    // Handle Exception
    // 1) define a exception handler 2) throw a illegalexception in controller
    @ExceptionHandler
    @ResponseBody
    public String errorMessage(Exception exp) {

        return  "Error: " + exp.getMessage();
    }

    @RequestMapping (path = {"/admin"}, method = {RequestMethod.GET})
    @ResponseBody
    public String getAdmin(@RequestParam("key") int key) {

        if (key != 1)
            throw new IllegalArgumentException("Invalid ID for admin.");

        return "Admin: " + key;
    }
}
