package pl.uncleglass.cloudstorage.service;

import org.springframework.stereotype.Service;
import pl.uncleglass.cloudstorage.mapper.NoteMapper;
import pl.uncleglass.cloudstorage.model.Note;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note) {
        return noteMapper.insert(note);
    }

    public List<Note> getNotes(int usersId) {
        return noteMapper.selectNotes(usersId);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }

    public int updateNote(Note note) {
        return noteMapper.update(note);
    }
}
