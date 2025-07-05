package com.example.vaksinasiservice.repository;

import com.example.vaksinasiservice.model.RiwayatVaksinasi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RiwayatVaksinasiRepository extends JpaRepository<RiwayatVaksinasi, Long> {
    List<RiwayatVaksinasi> findByName(String name);
}
