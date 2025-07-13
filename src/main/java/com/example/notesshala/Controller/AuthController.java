package com.example.notesshala.Controller;

import com.example.notesshala.Model.User;
import com.example.notesshala.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 1. Show the registration form (GET /auth/register)
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";                       // renders register.html
    }

    // 2. Handle form submission (POST /auth/register)
    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        userService.register(user);
        // After successful registration, redirect to login
        return "redirect:/login";
    }
}
