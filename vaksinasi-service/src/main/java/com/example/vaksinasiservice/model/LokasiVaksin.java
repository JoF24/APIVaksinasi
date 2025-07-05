package com.example.vaksinasiservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lokasiVaksin")
public class LokasiVaksin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaLokasi;
    private String namaVaksin;
    private int jumlah;

    // Getter & Setter
    public Long getId() {return id;}   
    public void setId(Long id) {this.id = id;}

    public String getNamaLokasi() {return namaLokasi;}
    public void setNamaLokasi(String namaLokasi) {this.namaLokasi = namaLokasi;}

    public String getNamaVaksin() {return namaVaksin;}
    public void setNamaVaksin(String namaVaksin) {this.namaVaksin = namaVaksin;}

    public int getJumlah() {return jumlah;}
    public void setJumlah(int jumlah) {this.jumlah = jumlah;}
}
