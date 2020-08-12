package pl.uncleglass.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String addNote(Note note,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes) {
        note.setUserId(userService.getAuthenticatedUsersId(authentication));
        noteService.addNote(note);
        redirectAttributes.addFlashAttribute("showNoteTab", true);
        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNot(@PathVariable Integer noteId, RedirectAttributes redirectAttributes) {
        noteService.deleteNote(noteId);
        redirectAttributes.addFlashAttribute("showNoteTab", true);
        return "redirect:/home";
    }
}
