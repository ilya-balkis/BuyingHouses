package by.buyinghouses.service;

import by.buyinghouses.domain.Accommodation;
import by.buyinghouses.domain.User;
import by.buyinghouses.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AccommodationService {

    private final static boolean ADDED = true;
    private final static boolean NOT_ADDED = false;
    private final static boolean WAITED = true;

    private final AccommodationRepository accommodationRepository;
    private final FileService fileService;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository, FileService fileService) {
        this.accommodationRepository = accommodationRepository;
        this.fileService = fileService;
    }

    public boolean addAccommodation(Accommodation accommodation) {

        Accommodation accommodationFromDB = accommodationRepository.findByName(accommodation.getName());
        boolean isAdded = ADDED;

        if (accommodationFromDB != null) {
            isAdded = NOT_ADDED;
        } else {
            accommodationRepository.save(accommodation);
        }

        return isAdded;
    }

    public Iterable<Accommodation> findAccommodations() {
        return accommodationRepository.findAll();
    }

    public Accommodation findAccommodation(String accommodationName) {
        return accommodationRepository.findByName(accommodationName);
    }

    public void deleteAccommodation(String accommodationName, String fileName) throws IOException {
        fileService.deleteImage(fileName);
        Accommodation accommodation = accommodationRepository.findByName(accommodationName);
        accommodationRepository.delete(accommodation);
    }

    public void acceptAccommodation(String accommodationName) {

        Accommodation accommodation = accommodationRepository.findByName(accommodationName);
        accommodation.setWaited(false);
        accommodationRepository.save(accommodation);
    }

    public void fillAccommodation(
            Accommodation accommodation, String fileName,
            User user, Boolean isFurniture, Boolean isInternet) {

        accommodation.setFileName(fileName);
        accommodation.setOwner(user);
        accommodation.setInternet(isInternet);
        accommodation.setFurniture(isFurniture);
        accommodation.setWaited(WAITED);
    }

}