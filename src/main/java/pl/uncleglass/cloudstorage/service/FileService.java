package pl.uncleglass.cloudstorage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.uncleglass.cloudstorage.mapper.FileMapper;
import pl.uncleglass.cloudstorage.model.File;

import java.io.IOException;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int uploadFile(MultipartFile multipartFile, int userId) throws IOException {
        File file = new File();
        file.setFilename(multipartFile.getName());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(Long.toString(multipartFile.getSize()));
        file.setUserId(userId);
        file.setFileData(multipartFile.getBytes());
        return fileMapper.insert(file);
    }
}
