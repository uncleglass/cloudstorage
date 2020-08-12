package pl.uncleglass.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.uncleglass.cloudstorage.model.Credential;
import pl.uncleglass.cloudstorage.service.CredentialService;
import pl.uncleglass.cloudstorage.service.UserService;

import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/decrypt/{credentialId}")
    @ResponseBody
    public List<String> decryptPassword(@PathVariable Integer credentialId) {
        return Arrays.asList(credentialService.getDecryptedPassword(credentialId));
    }
}
