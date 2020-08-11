package pl.uncleglass.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.uncleglass.cloudstorage.model.Note;
import pl.uncleglass.cloudstorage.service.NoteService;
import pl.uncleglass.cloudstorage.service.UserService;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addNote(Note note, Authentication authentication) {
        note.setUserId(userService.getAuthenticatedUsersId(authentication));
        noteService.addNote(note);
        return "redirect:/home";
    }
}
