package by.buyinghouses.controller;

import by.buyinghouses.domain.Accommodation;
import by.buyinghouses.domain.User;
import by.buyinghouses.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private static final String PROFILE = "profile";
    private static final String USER_PROFILE = "userProfile";

    private final AccommodationService accommodationService;

    @Autowired
    public ProfileController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    public String getProfile(
            @AuthenticationPrincipal User user,
            Model model) {

        Iterable<Accommodation> accommodations = accommodationService.findAccommodations();

        model.addAttribute("user", user);
        model.addAttribute("accommodations", accommodations);

        return USER_PROFILE;
    }

    @PostMapping("/delete")
    public String postProfile(
            @RequestParam String accommodationName,
            @RequestParam String fileName
    ) throws IOException {

        accommodationService.deleteAccommodation(accommodationName, fileName);

        return "redirect:/" + PROFILE;
    }

}