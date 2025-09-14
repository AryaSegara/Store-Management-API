# Backend Technical Test - Store Management API

Ini adalah proyek REST API yang dibangun sebagai bagian dari proses seleksi teknis. API ini berfungsi untuk mengelola data master Provinsi, Cabang, dan Toko di seluruh Indonesia. Proyek ini dibangun dengan arsitektur yang skalabel dan efisien, serta dilengkapi dengan fitur keamanan berbasis JWT.

---

### ## 🚀 Teknologi yang Digunakan

* **Java 21**
* **Spring Boot 3.5.5**
* **Spring Security 6.x (dikelola oleh Spring Boot)**
* **Spring Web (dengan embedded Tomcat)**
* **JPA (Hibernate)**
* **JJWT (Java JWT) untuk otentikasi token**
* **SpringDoc OpenAPI untuk dokumentasi API**
* **PostgreSQL**
* **Lombok**
* **Maven (sebagai build tool)**

---

### ## 🛠️ Cara Menjalankan Proyek

Pastikan Anda telah menginstal prasyarat berikut: **Git, JDK 21, dan PostgreSQL Server.**

**1. Clone Repositori**

```bash
git clone [https://github.com/akun-anda/nama-proyek.git](https://github.com/akun-anda/nama-proyek.git)
cd nama-proyek
```

**2. Install JDK 21**
 **Unduh di sini dari situs resmi Oracle**
``` 
https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html
```


**3. Konfigurasi Database**

* Buka pgAdmin atau DBeaver atau `psql` dan buat sebuah database baru dengan nama `indomaret_db`.
* Buka file `src/main/resources/application.properties`.
* Sesuaikan konfigurasi berikut dengan pengaturan PostgreSQL lokal Anda:

```properties
#SpringDoc OpenAPI di Spring Boot.
springdoc.api-docs.path=/v3/api-docs

# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/indomaret_db
spring.datasource.username=postgres
spring.datasource.password=password_postgres_anda

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=org.postgresql.Driver

# JWT Secret Key (Ganti dengan key rahasia Anda sendiri)
jwt.secret-key=veryLongSecretKeyChangeMeToEnvironmentVariable_please_12345
jwt.token-expiration=86400000

```

**4. Build Proyek**
Gunakan Maven Wrapper untuk membersihkan dan meng-install semua dependensi. Perintah ini akan otomatis mengunduh Maven jika diperlukan.

* **Untuk pengguna Mac/Linux (di Terminal):**
    ```bash
    ./mvnw clean install
    ```
* **Untuk pengguna Windows (di Command Prompt):**
    ```bash
    mvnw.cmd clean install
    ```
* **Untuk pengguna Windows (di PowerShell):**
    ```powershell
    .\mvnw.cmd clean install
    ```

**5. Jalankan Aplikasi**
Setelah build berhasil, jalankan aplikasi. Aplikasi akan siap di port **8080**.

* **Untuk pengguna Mac/Linux (di Terminal):**
    ```bash
    ./mvnw spring-boot:run
    ```
* **Untuk pengguna Windows (di Command Prompt):**
    ```bash
    mvnw.cmd spring-boot:run
    ```
* **Untuk pengguna Windows (di PowerShell):**
    ```powershell
    .\mvnw.cmd spring-boot:run
    ```

### ## 📖 Panduan Penggunaan API via Swagger UI

Cara termudah untuk menguji semua fungsionalitas API adalah melalui Swagger UI yang sudah terintegrasi.

**URL Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

#### **Langkah-langkah Otentikasi (Wajib Dilakukan Pertama Kali)**

Karena sebagian besar endpoint memerlukan otentikasi, ikuti alur di bawah ini untuk mendaftar, login, dan mengaktifkan akses ke semua endpoint.

**1. Registrasi Pengguna Baru**
* Buka Swagger UI, cari bagian `auth-controller`, lalu buka endpoint `POST /api/auth/register`.
* Klik tombol **"Try it out"**.
* Isi *Request body* dengan data pengguna baru. Contoh:
    ```json
    {
      "fullName": "Contoh",
      "username": "contoh123",
      "password": "password123"
    }
    ```
* Klik **"Execute"**. Anda akan mendapat respons bahwa registrasi berhasil.

**2. Login untuk Mendapatkan Token**
* Masih di `auth-controller`, buka endpoint `POST /api/auth/login`.
* Klik **"Try it out"** dan isi *Request body* dengan username dan password yang baru saja Anda daftarkan.
* Klik **"Execute"**.

**3. Copy Token JWT**
* Jika login berhasil, Anda akan mendapatkan respons `200`. Di dalam *Response body*, cari field `"token"`.
* **Copy semua teks panjang** yang ada di dalam tanda kutip tersebut. Teks ini adalah kunci akses Anda.
    ```json
    {
      "success": true,
      "message": "Login successful",
      "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzU3ODk4MjIyLCJleHAiOjE3NTc5ODQ2MjJ9.ABCDEFG12345HIJKLMNOP"
      }
    }
    ```

**4. Atur Otorisasi Global 🔑**
* Scroll ke bagian paling atas halaman Swagger UI, lalu klik tombol hijau **"Authorize"**.
* Sebuah window "Available authorizations" akan muncul. Di dalam kotak isian **Value**, tempel token yang sudah Anda copy.
* **PENTING:** Tambahkan kata `Bearer ` (diikuti spasi) di depan token Anda.
    * Contoh: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9l...`
* Klik tombol **"Authorize"**, lalu **"Close"**.

**5. Selesai! Anda Siap Menguji**
Sekarang, ikon gembok di sebelah kanan tombol "Authorize" akan terlihat terkunci 🔒. Ini artinya Anda sudah terotentikasi dan bisa mencoba semua endpoint yang memerlukan login (seperti `GET /api/provinces`, `POST /api/stores`, dll.) tanpa mendapatkan error `401 Unauthorized`.


---

