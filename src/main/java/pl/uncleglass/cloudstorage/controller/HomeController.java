package pl.uncleglass.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.uncleglass.cloudstorage.service.FileService;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String homeView(Model model) {
        model.addAttribute("files", fileService.getFiles());
        return "home";
    }
}
