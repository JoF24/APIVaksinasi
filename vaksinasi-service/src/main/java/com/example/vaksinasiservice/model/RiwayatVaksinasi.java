package com.example.vaksinasiservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "riwayatVaksinasi")
public class RiwayatVaksinasi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String jenisVaksin;
    private String lokasi;
    private String petugas;
    private String tanggal;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getJenisVaksin() { return jenisVaksin; }
    public void setJenisVaksin(String jenisVaksin) { this.jenisVaksin = jenisVaksin; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public String getPetugas() { return petugas; }
    public void setPetugas(String petugas) { this.petugas = petugas; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
}