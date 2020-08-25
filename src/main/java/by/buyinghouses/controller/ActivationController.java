package by.buyinghouses.controller;

import by.buyinghouses.service.MessageCreatorService;
import by.buyinghouses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ActivationController {

    public static final String LOGIN = "login";

    private final UserService userService;
    private final MessageCreatorService messageCreatorService;

    @Autowired
    public ActivationController(UserService userService, MessageCreatorService messageCreatorService) {
        this.userService = userService;
        this.messageCreatorService = messageCreatorService;
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {

        String message;
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            message = messageCreatorService.createSuccessfullyActivatedMessage();
        } else {
            message = messageCreatorService.createActivationCodeNotFoundMessage();
        }

        model.addAttribute("message", message);

        return LOGIN;
    }

}