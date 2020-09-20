package by.buyinghouses.controller;

import by.buyinghouses.domain.Accommodation;
import by.buyinghouses.domain.User;
import by.buyinghouses.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/editPanel")
public class EditController {

    private static final String EDITOR = "editPanel";
    private static final String PROFILE = "profile";

    private final AccommodationService accommodationService;

    @Autowired
    public EditController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping("{id}")
    public String getEditController(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestParam String accommodationName,
            Model model
    ) {

        Accommodation accommodation = accommodationService.findAccommodation(accommodationName);
        model.addAttribute("user", user);
        model.addAttribute("accommodation", accommodation);

        return EDITOR;
    }

    @PostMapping("{id}")
    public String putEditController(
            @PathVariable Long id,
            @RequestParam BigDecimal cost,
            @RequestParam String accommodationName
    ) {

        accommodationService.updateAccommodationCost(cost, accommodationName);
        return "redirect:/" + PROFILE;
    }
}