package me.ogustaflor.notesapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import me.ogustaflor.notesapp.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByFolderId(Long folder_id);
    Optional<Note> findByIdAndFolderId(Long id, Long folder_id);
}
