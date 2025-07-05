package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<?> all(HttpSession session) {
        User user = (User) session.getAttribute("user");

        // Cek apakah sudah login
        if (user == null) {
            System.out.println("Belum login atau session tidak aktif");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Belum login atau session tidak aktif");
        }

        // Cek apakah user memiliki role ADMIN
        if ("admin".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.ok(repo.findAll());
        } else {
            System.out.println("Anda tidak memiliki akses ke fitur ini");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Anda tidak memiliki akses ke fitur ini");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCurrentUser(@RequestBody User updatedData, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Belum login atau session tidak aktif");
        }
        // Ambil data user asli dari DB untuk update
        User user = repo.findById(sessionUser.getId()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User tidak ditemukan");
        }

        // Update hanya field yang dikirim
        if (updatedData.getName() != null) user.setName(updatedData.getName());
        if (updatedData.getUsername() != null) user.setUsername(updatedData.getUsername());
        if (updatedData.getPassword() != null) user.setPassword(updatedData.getPassword());

        repo.save(user);

        // Perbarui session agar info baru tersimpan juga
        session.setAttribute("user", user);

        return ResponseEntity.ok("Data berhasil diperbarui");
    }
}
