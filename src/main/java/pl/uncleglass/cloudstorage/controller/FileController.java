package pl.uncleglass.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.uncleglass.cloudstorage.model.User;
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

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) throws IOException {
        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("fileUploadEmpty", true);
            return "redirect:/home";
        }
        User user = userService.getUser(authentication.getName());
        fileService.uploadFile(multipartFile, user.getUserId());
        redirectAttributes.addFlashAttribute("fileUploadSuccess", true);
        return "redirect:/home";
    }
}
