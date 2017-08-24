package com.appdirect.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Spring MVC controller to handle the content on UI.
 */
@Controller
public class MainController {

    /**
     * Maps the index page
     * @return Returns the content template which displays subscription details.
     */
    @RequestMapping("/")
    public String content() {
        return "content";
    }

}
