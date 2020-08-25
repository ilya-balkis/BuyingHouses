package by.buyinghouses.controller;

import by.buyinghouses.domain.Accommodation;
import by.buyinghouses.domain.User;
import by.buyinghouses.service.AccommodationService;
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

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/adminPanel")
public class AdminController {

    private static final String ADMIN_PANEL = "adminPanel";

    private final AccommodationService accommodationService;

    @Autowired
    public AdminController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    public String getAdminPanel(
            @AuthenticationPrincipal User user,
            Model model) {

        Iterable<Accommodation> accommodations = accommodationService.findAccommodations();
        model.addAttribute("user", user);
        model.addAttribute("accommodations", accommodations);

        return ADMIN_PANEL;
    }

    @PostMapping("/accept")
    public String postAccess(
            @RequestParam String accommodationName) {

        accommodationService.acceptAccommodation(accommodationName);

        return "redirect:/" + ADMIN_PANEL;
    }

    @PostMapping("/delete")
    public String postDelete(
            @RequestParam String accommodationName,
            @RequestParam String fileName) throws IOException {

        accommodationService.deleteAccommodation(accommodationName, fileName);

        return "redirect:/" + ADMIN_PANEL;
    }
}