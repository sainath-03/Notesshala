package com.example.notesshala.Controller;

import com.example.notesshala.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ViewController {

    private final NoteService noteService;

    public ViewController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Home page after login
    @GetMapping({"/", "/home"})
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "home";
    }

    // Custom login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Registration form
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // List all notes for the logged-in user
    @GetMapping("/notes")
    public String listNotes(Model model, Principal principal) {
        model.addAttribute("notes", noteService.findByUsername(principal.getName()));
        return "notes";
    }

    // File-upload form
    @GetMapping("/notes/upload")
    public String uploadForm() {
        return "upload";
    }
}
