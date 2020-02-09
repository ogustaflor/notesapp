package me.ogustaflor.notesapp.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.ogustaflor.notesapp.models.Folder;
import me.ogustaflor.notesapp.repositories.FolderRepository;

@RestController
public class FolderController {
    @Autowired
    private FolderRepository folderRepository;

    @RequestMapping(value = "/folders", method = RequestMethod.GET)
    public List<Folder> Index() {
        return folderRepository.findAll();
    }

    @RequestMapping(value = "/folders", method =  RequestMethod.POST)
    public Folder Store(@Valid @RequestBody Folder request) {
        return folderRepository.save(request);
    }

    @RequestMapping(value = "/folders/{id}", method = RequestMethod.GET)
    public ResponseEntity<Folder> Show(@PathVariable(value = "id") Long id) {
        Optional<Folder> filteredFolder = folderRepository.findById(id);
        if (filteredFolder.isPresent()) {
            Folder folder = filteredFolder.get();
            return new ResponseEntity<Folder>(folder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/folders/{id}", method =  RequestMethod.PATCH)
    public ResponseEntity<Folder> Update(@Valid @RequestBody Folder request, @PathVariable(value = "id") Long id) {
        Optional<Folder> filteredFolder = folderRepository.findById(id);
        if (filteredFolder.isPresent()) {
            Folder folder = filteredFolder.get();
            folder.setName(request.getName());
            folderRepository.save(folder);
            return new ResponseEntity<Folder>(folder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/folders/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Destroy(@PathVariable(value = "id") Long id) {
        Optional<Folder> filteredFolder = folderRepository.findById(id);
        if (filteredFolder.isPresent()) {
            Folder folder = filteredFolder.get();
            folderRepository.delete(folder);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
