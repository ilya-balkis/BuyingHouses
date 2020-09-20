package by.buyinghouses.service;

import by.buyinghouses.domain.Accommodation;
import by.buyinghouses.domain.User;
import by.buyinghouses.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public List<Accommodation> findAccommodation(User user) {
        return accommodationRepository.findByOwner(user);
    }

    public void updateAccommodationCost(BigDecimal cost, String name){
        accommodationRepository.updateCost(cost, name);
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

    public void filterAccommodation(
            Iterable<Accommodation> accommodations, Accommodation accommodation,
            String isFurniture, String isInternet) {

        boolean furniture = false;
        boolean internet = false;

        if (isFurniture.equals("YES")) {
            furniture = true;
        }

        if (isInternet.equals("YES")) {
            internet = true;
        }

        weedOutByType(accommodations, accommodation.getType());

        if (!accommodation.getCity().isEmpty()) {
            weedOutByCity(accommodations, accommodation.getCity());
        }

        if (accommodation.getAmountOfRooms() != null) {
            weedOutByAmountOfRooms(accommodations, accommodation.getAmountOfRooms());
        }

        if (accommodation.getSquare() != null) {
            weedOutBySquare(accommodations, accommodation.getSquare());
        }

        weedOutByFurniture(accommodations, furniture);

        weedOutByInternet(accommodations, internet);

    }

    private void weedOutByType(
            Iterable<Accommodation> accommodations, String type) {

        Iterator<Accommodation> iterator = accommodations.iterator();
        while (iterator.hasNext()) {
            Accommodation accommodation = iterator.next();
            if (!accommodation.getType().equals(type)) {
                iterator.remove();
            }
        }
    }

    private void weedOutByCity(
            Iterable<Accommodation> accommodations, String city) {

        Iterator<Accommodation> iterator = accommodations.iterator();
        while (iterator.hasNext()) {
            Accommodation accommodation = iterator.next();
            if (!accommodation.getCity().toLowerCase().equals(city.toLowerCase())) {
                iterator.remove();
            }
        }
    }

    private void weedOutByAmountOfRooms(
            Iterable<Accommodation> accommodations, int amountOfRooms) {

        Iterator<Accommodation> iterator = accommodations.iterator();
        while (iterator.hasNext()) {
            Accommodation accommodation = iterator.next();
            if (accommodation.getAmountOfRooms() != amountOfRooms) {
                iterator.remove();
            }
        }
    }

    private void weedOutBySquare(
            Iterable<Accommodation> accommodations, float square) {

        Iterator<Accommodation> iterator = accommodations.iterator();
        while (iterator.hasNext()) {
            Accommodation accommodation = iterator.next();
            if (accommodation.getSquare() != square) {
                iterator.remove();
            }
        }
    }

    private void weedOutByFurniture(
            Iterable<Accommodation> accommodations, boolean furniture) {

        Iterator<Accommodation> iterator = accommodations.iterator();
        while (iterator.hasNext()) {
            Accommodation accommodation = iterator.next();
            if (accommodation.isFurniture() != furniture) {
                iterator.remove();
            }
        }
    }

    private void weedOutByInternet(
            Iterable<Accommodation> accommodations, boolean internet) {

        Iterator<Accommodation> iterator = accommodations.iterator();
        while (iterator.hasNext()) {
            Accommodation accommodation = iterator.next();
            if (accommodation.isInternet() != internet) {
                iterator.remove();
            }
        }
    }
}