package by.buyinghouses.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDService {

    public String createUUID() {
        return UUID.randomUUID().toString();
    }

}