# Sistem Informasi Vaksinasi – Microservice Architecture

Proyek ini merupakan sistem informasi vaksinasi berbasis **Spring Boot** dengan arsitektur **microservice** yang dikembangkan menggunakan bahasa Java, yang terdiri dari tiga layanan utama:

- **Gateway Service** – Bertindak sebagai API Gateway yang mengatur routing ke microservice lain.
- **User Service** – Mengelola autentikasi dan data akun pengguna.
- **Vaksinasi Service** – Mengelola data vaksinasi, lokasi vaksin, dan informasi vaksin.

## 🧩 Fitur Utama

### 👩‍⚕️ Admin
- Register, Login, Logout
- Ubah informasi akun
- Kelola semua akun pengguna
- Kelola riwayat vaksinasi (tambah, lihat, ubah, hapus)
- Kelola lokasi vaksinasi (tambah, lihat, ubah, hapus)
- Kelola informasi vaksin (tambah, lihat, ubah, hapus)

### 👤 User Biasa
- Register, Login, Logout
- Ubah informasi akun
- Lihat riwayat vaksinasi milik sendiri
- Lihat informasi lokasi vaksinasi
- Lihat informasi vaksin

## 🛠️ Teknologi yang Digunakan
- Java + Spring Boot
- Spring Web, Spring Data JPA, Spring Security
- MySQL (via phpMyAdmin) – `userdb`, `vaksinasidb`
- JWT Authentication
- Docker & Docker Compose
- API Gateway

## 📦 Struktur Microservice
```

📁 gateway-service
📁 user-service
📁 vaksinasi-service
📄 docker-compose.yml

````

Sistem ini menggunakan **JWT (JSON Web Token)** untuk autentikasi.
Token dikirim melalui header:

```
Authorization: Bearer <token>
```

pada setiap permintaan ke endpoint yang memerlukan autentikasi.
