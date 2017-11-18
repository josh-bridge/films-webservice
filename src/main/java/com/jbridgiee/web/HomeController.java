package com.jbridgiee.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author josh.bridge
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index() {
        return "WEB-INF/jsp/index.jsp";
    }

}
