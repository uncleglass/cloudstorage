package pl.uncleglass.cloudstorage.service;

import org.springframework.stereotype.Service;
import pl.uncleglass.cloudstorage.mapper.NoteMapper;
import pl.uncleglass.cloudstorage.model.Note;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note) {
        return noteMapper.insert(note);
    }
}
