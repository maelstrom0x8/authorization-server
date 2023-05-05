package com.aeflheim.quasar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuasarController {

    @GetMapping("/clients")
    public String clientPage() {
        return "index.html";
    }

    
}
