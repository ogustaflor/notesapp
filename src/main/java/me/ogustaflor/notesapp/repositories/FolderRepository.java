package me.ogustaflor.notesapp.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import me.ogustaflor.notesapp.models.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {}
