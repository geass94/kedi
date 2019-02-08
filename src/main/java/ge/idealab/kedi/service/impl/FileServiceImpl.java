package ge.idealab.kedi.service.impl;

import ge.idealab.kedi.config.FileStorageProperties;
import ge.idealab.kedi.exception.FileStorageException;
import ge.idealab.kedi.exception.MyFileNotFoundException;
import ge.idealab.kedi.model.File;
import ge.idealab.kedi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {
    private final Path fileStorageLocation;

    @Autowired
    public FileServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public File uploadFile(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        File file = new File();
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            String uuid = UUID.randomUUID().toString()+"."+multipartFile.getOriginalFilename().split("\\.")[1].toLowerCase();
            Path targetLocation = this.fileStorageLocation.resolve(uuid);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/file/")
                    .path(uuid)
                    .toUriString();
            file.setFileSize(multipartFile.getSize());
            file.setFileUrl(fileDownloadUri);
            file.setFileType(multipartFile.getContentType());
            file.setName(uuid);
            file.setOriginalName(multipartFile.getOriginalFilename());
            file.setExtension(multipartFile.getOriginalFilename().split("\\.")[1].toLowerCase());
            return file;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
