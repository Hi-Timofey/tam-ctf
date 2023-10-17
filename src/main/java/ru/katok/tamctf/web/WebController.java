package ru.katok.tamctf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }
}