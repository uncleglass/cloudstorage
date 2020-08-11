package pl.uncleglass.cloudstorage.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.uncleglass.cloudstorage.mapper.FileMapper;
import pl.uncleglass.cloudstorage.model.File;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFile(int fileId) {
        return fileMapper.select(fileId);
    }

    public boolean isExist(int fileId) {
        return getFile(fileId) != null;
    }

    public List<File> getFiles() {
        return fileMapper.selectAll();
    }

    public int uploadFile(MultipartFile multipartFile, int userId) throws IOException {
        File file = new File();
        file.setFilename(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(convertSize(multipartFile.getSize()));
        file.setUserId(userId);
        file.setFileData(multipartFile.getBytes());
        return fileMapper.insert(file);
    }

    private String convertSize(Long size) {
        if (size > 1_048_576) {
            return size / 1048576 + " MB";
        }
        if (size > 1_023) {
            return size / 1024 + " KB";
        }
        return size + " B";
    }

    public void deleteFile(int fileId) {
        fileMapper.delete(fileId);
    }
}