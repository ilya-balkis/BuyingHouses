package by.buyinghouses.controller;

import by.buyinghouses.domain.User;
import by.buyinghouses.service.MessageCreatorService;
import by.buyinghouses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private static final String REGISTRATION = "registration";
    private static final String LOGIN = "login";
    private static final boolean CORRECT = true;
    private static final boolean NOT_CORRECT = false;

    private final UserService userService;
    private final MessageCreatorService messageCreatorService;

    @Autowired
    public RegistrationController(UserService userService, MessageCreatorService messageCreatorService) {
        this.userService = userService;
        this.messageCreatorService = messageCreatorService;
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(
            @Valid User user,
            @RequestParam String repeatedPassword,
            BindingResult bindingResult,
            Model model)
    {

        boolean isRepeatedPasswordEmpty = StringUtils.isEmpty(repeatedPassword);
        boolean correct = CORRECT;

        if(isRepeatedPasswordEmpty){
            String message = messageCreatorService.createEmptyRepeatedPasswordMessage();
            model.addAttribute("repeatedPasswordError", message);

            correct = NOT_CORRECT;
        }

        if(!user.getPassword().equals(repeatedPassword)){
            String message = messageCreatorService.createDifferentPasswordsMessage();
            model.addAttribute("repeatedPasswordError", message);

            correct = NOT_CORRECT;
        }

        if(bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            correct = NOT_CORRECT;
        }

        if(!correct){
            return REGISTRATION;
        }

        if (!userService.addUser(user)) {
            String message = messageCreatorService.createUserExistMessage();
            model.addAttribute("userError", message);

            return REGISTRATION;
        }

        return "redirect:/" + LOGIN;
    }

}