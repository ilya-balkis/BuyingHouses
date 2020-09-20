package by.buyinghouses.controller;

import by.buyinghouses.domain.Accommodation;
import by.buyinghouses.domain.User;
import by.buyinghouses.service.AccommodationService;
import by.buyinghouses.service.FileService;
import by.buyinghouses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/deletePanel")
public class DeleteController {

    private static final String DELETE_PANEL = "deletePanel";

    private final UserService userService;
    private final AccommodationService accommodationService;
    private final FileService fileService;

    @Autowired
    public DeleteController(UserService userService, AccommodationService accommodationService , FileService fileService) {
        this.userService = userService;
        this.accommodationService = accommodationService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getDeleteController(
            @AuthenticationPrincipal User user,
            Model model) {

        Iterable<User> users = userService.findUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", user);

        return DELETE_PANEL;
    }

    @PostMapping
    public String postDeleteController(
            @AuthenticationPrincipal User user,
            @RequestParam String userName){

        User deletedUser = userService.findUser(userName);
        List<Accommodation> accommodations = accommodationService.findAccommodation(deletedUser);
        List<String> files = accommodations.stream().
                map(Accommodation::getFileName).
                collect(Collectors.toCollection(ArrayList::new));

        if(!user.getUserName().equals(userName)){
            userService.deleteUser(userName);
            files.forEach(p -> {
                try {
                    fileService.deleteImage(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return "redirect:/" + DELETE_PANEL;
    }

}