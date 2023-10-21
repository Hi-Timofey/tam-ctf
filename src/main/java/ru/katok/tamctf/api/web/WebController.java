package ru.katok.tamctf.api.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @RequestMapping("/404")
    public String render404(Model model) {
        // Add model attributes
        return "404";
    }
}
