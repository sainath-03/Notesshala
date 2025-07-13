package com.example.notesshala.Controller;


import com.example.notesshala.Model.Note;
import com.example.notesshala.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;


import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/notes/upload")
    public String handleUpload(@RequestParam("branch") String branch,
                               @RequestParam("about") String about,
                               @RequestParam("file") MultipartFile file,
                               Principal principal) throws Exception {
        // build a Note entity
        Note n = new Note();
        n.setBranch(branch);
        n.setAbout(about);
        // service will set owner based on username
        noteService.upload(n, file, principal.getName());
        return "redirect:/notes";
    }
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws Exception {
        Path path = noteService.getFilePath(filename);
        Resource res = new org.springframework.core.io.UrlResource(path.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(res);
    }

//    @GetMapping
//    public ResponseEntity<List<Note>> listByBranch(@RequestParam String branch){
//        List<Note> notes = noteService.listByBranch(branch);
//        return ResponseEntity.ok(notes);
//    }
//
//    @GetMapping("/{id}/download")
//    public ResponseEntity<Resource> download(@PathVariable Long id) throws Exception{
//        Note note = noteService.getById(id);         // you’ll add this method next
//        Path filePath = noteService.getFilePath(note.getFilename());
//        Resource res = new org.springframework.core.io.UrlResource(filePath.toUri());
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\"" + note.getFilename() + "\"")
//                .body(res);
//    }
}
