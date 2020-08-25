package by.buyinghouses.controller;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableAsync
public class WelcomeController {

    private static final String WELCOME = "welcome";

    @GetMapping("/")
    public String main() {
        return WELCOME;
    }

}