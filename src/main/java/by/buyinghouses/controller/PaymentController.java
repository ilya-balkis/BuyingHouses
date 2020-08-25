package by.buyinghouses.controller;

import by.buyinghouses.domain.Accommodation;
import by.buyinghouses.domain.User;
import by.buyinghouses.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final String PAYMENT = "payment";
    private static final String BUYING_ACCOMMODATION = "buyingAccommodation";

    private final AccommodationService accommodationService;

    @Autowired
    public PaymentController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping("{id}")
    public String getPayment(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestParam String accommodationName,
            Model model
    ) {

        Accommodation accommodation = accommodationService.findAccommodation(accommodationName);
        model.addAttribute("user", user);
        model.addAttribute("accommodation", accommodation);

        return PAYMENT;
    }

    @PostMapping("{id}")
    public String postPayment(
            @PathVariable Long id,
            @RequestParam String accommodationName,
            @RequestParam String fileName
    ) throws IOException {

        accommodationService.deleteAccommodation(accommodationName, fileName);

        return "redirect:/" + BUYING_ACCOMMODATION;
    }

}