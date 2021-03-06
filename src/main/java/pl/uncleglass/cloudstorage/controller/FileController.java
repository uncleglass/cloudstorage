package pl.uncleglass.cloudstorage.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.uncleglass.cloudstorage.model.File;
import pl.uncleglass.cloudstorage.service.FileService;
import pl.uncleglass.cloudstorage.service.UserService;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/{fileId}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Integer fileId) {
        File file = fileService.getFile(fileId);
        Resource resource = new ByteArrayResource(file.getFileData());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        if (fileService.isDeletingAllowed(fileId, userService.getAuthenticatedUsersId(authentication))) {
            fileService.deleteFile(fileId);
            redirectAttributes.addFlashAttribute("fileDeleted", true);
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("fileError", true);
        return "redirect:/home";
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("fileUploadEmpty", true);
            return "redirect:/home";
        }
        if (fileService.isNameOccupied(multipartFile.getOriginalFilename())) {
            redirectAttributes.addFlashAttribute("fileNameOccupied", true);
            return "redirect:/home";
        }
        try {
            fileService.uploadFile(multipartFile, userService.getAuthenticatedUsersId(authentication));
            redirectAttributes.addFlashAttribute("fileUploaded", true);
            return "redirect:/home";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("fileError", true);
            return "redirect:/home";
        }
    }
}
