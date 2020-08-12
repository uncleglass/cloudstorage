package pl.uncleglass.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.uncleglass.cloudstorage.service.CredentialService;
import pl.uncleglass.cloudstorage.service.FileService;
import pl.uncleglass.cloudstorage.service.NoteService;
import pl.uncleglass.cloudstorage.service.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(FileService fileService,
                          UserService userService,
                          NoteService noteService,
                          CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String homeView(Model model, Authentication authentication) {
        int authenticatedUsersId = userService.getAuthenticatedUsersId(authentication);
        model.addAttribute("files", fileService.getFiles(authenticatedUsersId));
        model.addAttribute("notes", noteService.getNotes(authenticatedUsersId));
        model.addAttribute("credentials", credentialService.getCredentials(authenticatedUsersId));
        return "home";
    }
}
