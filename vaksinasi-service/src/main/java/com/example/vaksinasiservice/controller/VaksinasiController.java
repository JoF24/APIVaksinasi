package com.example.vaksinasiservice.controller;

import com.example.vaksinasiservice.model.RiwayatVaksinasi;
import com.example.vaksinasiservice.model.JenisVaksin;
import com.example.vaksinasiservice.model.LokasiVaksin;
import com.example.vaksinasiservice.repository.RiwayatVaksinasiRepository;
import com.example.vaksinasiservice.repository.JenisVaksinRepository;
import com.example.vaksinasiservice.repository.LokasiVaksinRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaksinasi")
public class VaksinasiController {

    private final RiwayatVaksinasiRepository riwayatRepo;
    private final JenisVaksinRepository jenisRepo;
    private final LokasiVaksinRepository lokasiRepo;

    public VaksinasiController(
            RiwayatVaksinasiRepository riwayatRepo,
            JenisVaksinRepository jenisRepo,
            LokasiVaksinRepository lokasiRepo
    ) {
        this.riwayatRepo = riwayatRepo;
        this.jenisRepo = jenisRepo;
        this.lokasiRepo = lokasiRepo;
    }

    // ================== RIWAYAT ==================

    @GetMapping("/riwayat")
    public ResponseEntity<?> getAllRiwayat(HttpServletRequest request) {
        String name = (String) request.getAttribute("name");
        String role = (String) request.getAttribute("role");

        if (name == null || role == null) {
            return ResponseEntity.status(401).body("Token tidak valid");
        }

        if ("admin".equals(role)) {
            return ResponseEntity.ok(riwayatRepo.findAll());
        } else if ("pengguna".equals(role)) {
            return ResponseEntity.ok(riwayatRepo.findByName(name));
        } else {
            return ResponseEntity.status(403).body("Peran tidak diizinkan");
        }
    }

    @PostMapping("/riwayat")
    public ResponseEntity<?> addRiwayat(@RequestBody RiwayatVaksinasi data, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(403).body("Hanya admin yang bisa menambahkan riwayat");
        }

        return ResponseEntity.ok(riwayatRepo.save(data));
    }

    @PutMapping("/riwayat/{id}")
    public ResponseEntity<?> updateRiwayat(@PathVariable Long id, @RequestBody RiwayatVaksinasi data, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(403).body("Hanya admin yang bisa mengubah riwayat");
        }

        return riwayatRepo.findById(id)
                .map(existing -> {
                    if (data.getName() != null) existing.setName(data.getName());
                    if (data.getJenisVaksin() != null) existing.setJenisVaksin(data.getJenisVaksin());
                    if (data.getLokasi() != null) existing.setLokasi(data.getLokasi());
                    if (data.getPetugas() != null) existing.setPetugas(data.getPetugas());
                    if (data.getTanggal() != null) existing.setTanggal(data.getTanggal());
                    return ResponseEntity.ok(riwayatRepo.save(existing));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/riwayat/{id}")
    public ResponseEntity<?> deleteRiwayat(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(403).body("Hanya admin yang bisa menghapus riwayat");
        }

        return riwayatRepo.findById(id)
                .map(riwayat -> {
                    riwayatRepo.delete(riwayat);
                    return ResponseEntity.ok("Riwayat berhasil dihapus");
                }).orElse(ResponseEntity.notFound().build());
    }

    // ================== JENIS VAKSIN ==================

    @GetMapping("/jenis")
    public List<JenisVaksin> getAllJenis() {
        return jenisRepo.findAll();
    }

    @PostMapping("/jenis")
    public ResponseEntity<?> addJenis(@RequestBody JenisVaksin data, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(403).body("Hanya admin yang bisa menambahkan jenis vaksin");
        }

        return ResponseEntity.ok(jenisRepo.save(data));
    }

    @PutMapping("/jenis/{id}")
    public ResponseEntity<?> updateJenis(@PathVariable Long id, @RequestBody JenisVaksin data, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(403).body("Hanya admin yang bisa mengubah jenis vaksin");
        }

        return jenisRepo.findById(id)
                .map(jenis -> {
                    if (data.getNama() != null) jenis.setNama(data.getNama());
                    if (data.getProdusen() != null) jenis.setProdusen(data.getProdusen());
                    if (data.getDeskripsi() != null) jenis.setDeskripsi(data.getDeskripsi());
                    return ResponseEntity.ok(jenisRepo.save(jenis));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/jenis/{id}")
    public ResponseEntity<?> deleteJenis(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(403).body("Hanya admin yang bisa menghapus jenis vaksin");
        }

        return jenisRepo.findById(id)
                .map(jenis -> {
                    jenisRepo.delete(jenis);
                    return ResponseEntity.ok("Jenis vaksin berhasil dihapus");
                }).orElse(ResponseEntity.notFound().build());
    }

    // ================== LOKASI VAKSIN ==================

    @GetMapping("/lokasi")
    public List<LokasiVaksin> getAllLokasi() {
        return lokasiRepo.findAll();
    }

    @PostMapping("/lokasi")
    public ResponseEntity<?> addLokasi(@RequestBody LokasiVaksin data, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(403).body("Hanya admin yang bisa menambahkan lokasi vaksin");
        }

        return ResponseEntity.ok(lokasiRepo.save(data));
    }

    @PutMapping("/lokasi/{id}")
    public ResponseEntity<?> updateLokasi(@PathVariable Long id, @RequestBody LokasiVaksin data, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(403).body("Hanya admin yang bisa mengubah lokasi vaksin");
        }

        return lokasiRepo.findById(id)
                .map(lokasi -> {
                    if (data.getNamaLokasi() != null) lokasi.setNamaLokasi(data.getNamaLokasi());
                    if (data.getNamaVaksin() != null) lokasi.setNamaVaksin(data.getNamaVaksin());
                    // Integer primitif, jadi periksa jika bukan 0
                    if (data.getJumlah() != 0) lokasi.setJumlah(data.getJumlah());
                    return ResponseEntity.ok(lokasiRepo.save(lokasi));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/lokasi/{id}")
    public ResponseEntity<?> deleteLokasi(@PathVariable Long id, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(403).body("Hanya admin yang bisa menghapus lokasi vaksin");
        }

        return lokasiRepo.findById(id)
                .map(lokasi -> {
                    lokasiRepo.delete(lokasi);
                    return ResponseEntity.ok("Lokasi vaksin berhasil dihapus");
                }).orElse(ResponseEntity.notFound().build());
    }
}
