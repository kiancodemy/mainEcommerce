package com.ecommerce.main.common.components;

import com.ecommerce.main.common.exception.InvalidFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/// saving files and paths into data base///
@Service
public class FileStorageService {
    private static final long MAX_SIZE = 3 * 1024 * 1024;
    private final Path root;
    private final String publicUrl;


    public FileStorageService(@Value("${app.upload-dir}") String uploadDir, @Value("${app.public-url}") String publicUrl) {
        this.root = Path.of(uploadDir).toAbsolutePath();
        this.publicUrl = publicUrl;

    }

    // returns the image address
    public String save(MultipartFile file, String folder) throws IOException {
        calculateSize(file);

        InputStream in = file.getInputStream();
        if (ImageIO.read(in) == null) {
            throw new InvalidFileException("File is not a valid image");
        }

        // 1. make a new name
        String name = UUID.randomUUID() + ".jpg";

        // 2. make sure the folder exists: uploads/folder
        Path folderPath = root.resolve(folder);

        Files.createDirectories(folderPath);

        file.transferTo(folderPath.resolve(name));

        return toPublicUrl(name,folder);

    }

    // final image url
    public String toPublicUrl(String path,String folder) {
        if (path == null) return null;
        return publicUrl + "files/" + folder +"/" + path;
    }

    // if size of image is more than 3MB
    private void calculateSize(MultipartFile file) {
        if (file.getSize() > MAX_SIZE) {
            throw new InvalidFileException("Image must be smaller than 3 MB");
        }
    }


}

