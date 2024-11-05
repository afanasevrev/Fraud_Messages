package com.example.CatchingScammers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ApplicationController {
    @GetMapping("/")
    private String getInfo() {
        return "form";
    }
}
