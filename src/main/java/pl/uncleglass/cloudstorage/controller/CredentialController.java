package pl.uncleglass.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.uncleglass.cloudstorage.model.Credential;
import pl.uncleglass.cloudstorage.service.CredentialService;
import pl.uncleglass.cloudstorage.service.UserService;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String addCredential(Credential credential,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        credential.setUserId(userService.getAuthenticatedUsersId(authentication));
        credentialService.addCredential(credential);
        redirectAttributes.addFlashAttribute("showCredentialTab", true);
        return "redirect:/home";
    }
}
