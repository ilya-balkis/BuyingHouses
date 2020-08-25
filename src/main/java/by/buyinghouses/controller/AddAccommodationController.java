package by.buyinghouses.controller;

import by.buyinghouses.domain.Accommodation;
import by.buyinghouses.domain.AccommodationType;
import by.buyinghouses.domain.User;
import by.buyinghouses.service.AccommodationService;
import by.buyinghouses.service.FileService;
import by.buyinghouses.service.MessageCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class AddAccommodationController {

    private static final String ADD_ACCOMMODATION = "addAccommodation";
    private static final String BUYING_ACCOMMODATION = "buyingAccommodation";

    private final AccommodationService accommodationService;
    private final FileService fileService;
    private final MessageCreatorService messageCreatorService;

    @Autowired
    public AddAccommodationController(AccommodationService accommodationService,
                                      FileService fileService,
                                      MessageCreatorService messageCreatorService) {
        this.accommodationService = accommodationService;
        this.fileService = fileService;
        this.messageCreatorService = messageCreatorService;
    }

    @GetMapping("/addAccommodation")
    public String getAddAccommodation(
            @AuthenticationPrincipal User user,
            Model model) {

        model.addAttribute("user", user);
        model.addAttribute("types", AccommodationType.values());

        return ADD_ACCOMMODATION;
    }

    @PostMapping("/addAccommodation")
    public String postAddAccommodation(
            @AuthenticationPrincipal User user,
            boolean isInternet,
            boolean isFurniture,
            MultipartFile file,
            @Valid Accommodation accommodation,
            BindingResult bindingResult,
            Model model
    ) throws IOException {

        if (bindingResult.hasErrors()) {

            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            model.addAttribute("user", user);
            model.addAttribute("types", AccommodationType.values());

            return ADD_ACCOMMODATION;
        }

        String fileName = fileService.createFileName(file);
        accommodationService.fillAccommodation(accommodation, fileName, user, isFurniture, isInternet);

        if (!accommodationService.addAccommodation(accommodation)) {

            String message = messageCreatorService.createAccommodationExistMessage();

            model.addAttribute("user", user);
            model.addAttribute("nameError", message);
            model.addAttribute("types", AccommodationType.values());

            return ADD_ACCOMMODATION;
        }

        fileService.saveImage(fileName, file);

        return "redirect:/" + BUYING_ACCOMMODATION;
    }
}