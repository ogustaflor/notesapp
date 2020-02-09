package me.ogustaflor.notesapp.controllers;

import java.util.Optional;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.ogustaflor.notesapp.models.Note;
import me.ogustaflor.notesapp.models.Folder;
import me.ogustaflor.notesapp.repositories.FolderRepository;
import me.ogustaflor.notesapp.repositories.NoteRepository;

@RestController
public class NoteController {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private FolderRepository folderRepository;

    @RequestMapping(value = "/folders/{folder_id}/notes", method = RequestMethod.GET)
    public List<Note> Index(@PathVariable(value = "folder_id") Long folderId) {
        return noteRepository.findByFolderId(folderId);
    }

    @RequestMapping(value = "/folders/{folder_id}/notes", method =  RequestMethod.POST)
    public ResponseEntity<Note> Store(@Valid @RequestBody Note request, @PathVariable(value = "folder_id") Long folderId) {
        Optional<Folder> filteredFolder = folderRepository.findById(folderId);
        if (filteredFolder.isPresent()) {
            Note note = request;
            Folder folder = filteredFolder.get();
            note.setFolder(folder);
            noteRepository.save(note);
            return new ResponseEntity<Note>(note, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/folders/{folder_id}/notes/{id}", method = RequestMethod.GET)
    public ResponseEntity<Note> Show(@PathVariable(value = "folder_id") Long folderId, @PathVariable(value = "id") Long id) {
        Optional<Note> filteredNote = noteRepository.findByIdAndFolderId(id, folderId);
        if (filteredNote.isPresent()) {
            Note note = filteredNote.get();
            return new ResponseEntity<Note>(note, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/folders/{folder_id}/notes/{id}", method =  RequestMethod.PATCH)
    public ResponseEntity<Note> Update(@Valid @RequestBody Note request, @PathVariable(value = "folder_id") Long folderId, @PathVariable(value = "id") Long id) {
        Optional<Note> filteredNote = noteRepository.findByIdAndFolderId(id, folderId);
        if (filteredNote.isPresent()) {
            Note note = filteredNote.get();
            note.setBody(request.getBody());
            noteRepository.save(note);
            return new ResponseEntity<Note>(note, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/folders/{folder_id}/notes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Destroy(@PathVariable(value = "folder_id") Long folderId, @PathVariable(value = "id") Long id) {
        Optional<Note> filteredNote = noteRepository.findByIdAndFolderId(id, folderId);
        if (filteredNote.isPresent()) {
            Note note = filteredNote.get();
            noteRepository.delete(note);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}