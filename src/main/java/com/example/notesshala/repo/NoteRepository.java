package com.example.notesshala.repo;

import com.example.notesshala.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long>
{
    List<Note> findByBranch(String branch);

    List<Note> findByOwnerUsername(String username);
}
