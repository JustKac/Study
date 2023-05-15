package br.com.segsat.restwhitspringbootandjava.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import br.com.segsat.restwhitspringbootandjava.config.FileStoreConfig;
import br.com.segsat.restwhitspringbootandjava.exceptions.FileStorageException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    // @Autowired
    public FileStorageService(FileStoreConfig fileStoreConfig) {
        Path path = Paths.get(fileStoreConfig.getUploadDir())
                                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException(
                "Could not create the directory where the uploaded files will be stored", e);
        }
    }

    public String storeFile(MultipartFile multipartFile){
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        try {
            if (fileName.contains("..")){
                throw new FileStorageException(
                    "Sorry, filename contains invalid path sequence '" + fileName + "'");
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException(
                "Could not store file " + fileName + ". Please try again.", e);
        }


    }
}
