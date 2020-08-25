package by.buyinghouses.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${upload.path}")
    private String path;

    private final UUIDService uuidService;

    @Autowired
    public FileService(UUIDService uuidService) {
        this.uuidService = uuidService;
    }

    public void saveImage(String fileName, MultipartFile file) throws IOException {

        createUploadFolder();
        upload(file, fileName);
    }

    public String createFileName(MultipartFile file) {

        String uuidFile = uuidService.createUUID();

        return createFileName(uuidFile, file);
    }

    public void deleteImage(String fileName) throws IOException {

        String stringPath = createPaths(fileName);
        Path filePath = Paths.get(stringPath);
        Files.delete(filePath);
    }

    private String createPaths(String fileName) {
        return path + "/" + fileName;
    }

    private void createUploadFolder() {

        File uploadFolder = new File(path);

        if (!uploadFolder.exists()) {
            uploadFolder.mkdir();
        }
    }

    private String createFileName(String uuid, MultipartFile file) {
        return uuid + "." + file.getOriginalFilename();
    }

    private void upload(MultipartFile file, String fileName) throws IOException {

        String filePath = createPaths(fileName);
        file.transferTo(new File(filePath));
    }
}