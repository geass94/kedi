package ge.idealab.kedi.service;

import ge.idealab.kedi.model.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    File uploadFile(MultipartFile multipartFile);
    Resource loadFileAsResource(String fileName);
}
