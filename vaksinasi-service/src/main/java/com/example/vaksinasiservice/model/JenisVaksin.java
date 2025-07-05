package com.example.vaksinasiservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jenisVaksin")
public class JenisVaksin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String produsen;
    private String deskripsi;

    // Getter & Setter
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNama() {return nama;}
    public void setNama(String nama) {this.nama = nama;}

    public String getProdusen() {return produsen;}
    public void setProdusen(String produsen) {this.produsen = produsen;}

    public String getDeskripsi() {return deskripsi;}
    public void setDeskripsi(String deskripsi) {this.deskripsi = deskripsi;}
}
