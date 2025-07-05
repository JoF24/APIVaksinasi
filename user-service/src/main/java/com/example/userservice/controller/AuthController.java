package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint untuk registrasi
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        if (repo.findByUsername(newUser.getUsername()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body("Username sudah digunakan!");
        }

        newUser.setRole("pengguna"); // default role USER
        return ResponseEntity.ok(repo.save(newUser));
    }

    // Endpoint untuk login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser, HttpSession session) {
        User user = repo.findByUsername(loginUser.getUsername());

        if (user == null) {
            System.out.println("USERNAME TIDAK DITEMUKAN: " + loginUser.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username atau password salah");
        }

        System.out.println("USERNAME DITEMUKAN: " + user.getUsername());
        System.out.println("PASSWORD DARI DB : " + user.getPassword());
        System.out.println("PASSWORD DARI INPUT : " + loginUser.getPassword());

        if (!user.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username atau password salah");
        }

        session.setAttribute("user", user);
        String token = jwtUtil.generateToken(user.getName(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login berhasil! Selamat datang, " + user.getName());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> currentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Belum login");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout berhasil");
    }
}
