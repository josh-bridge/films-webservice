package com.jbridgiee.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Simple controller for the web front end
 *
 * @author josh.bridge
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index() {
        return "/static/html/index.html";
    }

}
