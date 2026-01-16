# 🏥 Aplikasi Manajemen Data Pasien Rumah Sakit
*(Desktop Java Swing)*

Aplikasi ini merupakan program desktop berbasis **Java Swing** yang digunakan untuk **mengelola data pasien rumah sakit** secara sederhana, cepat, dan rapi.  
Seluruh data pasien disimpan **di memori (in-memory)** tanpa menggunakan database, sehingga aplikasi ini sangat cocok digunakan sebagai **studi kasus Struktur Data**, khususnya **Binary Search Tree (BST)** dan **Traversal**.

---

## 1) Tujuan Aplikasi
Aplikasi ini dibuat untuk membantu petugas rumah sakit dalam:
- **Menambah** data pasien baru
- **Mencari** data pasien berdasarkan **ID Pasien**
- **Menghapus** data pasien dengan aman
- Melihat daftar pasien yang **selalu terurut berdasarkan ID Pasien** secara otomatis

---

## 2) Studi Kasus: Mengapa Menggunakan Binary Search Tree (BST)?

### Masalah Dunia Nyata
Dalam lingkungan rumah sakit, data pasien dapat berjumlah **banyak (ratusan hingga ribuan)**.  
Aktivitas yang sering dilakukan antara lain:
- Mencari pasien berdasarkan **ID Pasien**
- Menambahkan pasien baru setiap hari
- Menghapus data pasien tertentu
- Menampilkan daftar pasien secara rapi dan terstruktur

Jika menggunakan struktur data biasa (misalnya array atau list), pencarian dan pengelolaan data akan menjadi kurang efisien.

### Alasan BST Digunakan
Aplikasi ini menggunakan **Binary Search Tree (BST)** karena:
- Data disimpan berdasarkan **patientId** (angka dan unik)
- Mendukung operasi utama secara efisien:
    - **Insert** → menambah data pasien
    - **Search** → mencari pasien berdasarkan ID
    - **Delete** → menghapus pasien berdasarkan ID
- Dengan traversal tertentu, data dapat ditampilkan **terurut otomatis** tanpa proses sorting tambahan

> Pada aplikasi ini, BST berperan sebagai **mesin internal** (back-end logic).  
> Pengguna tidak perlu memahami BST untuk dapat menggunakan aplikasi.

---

## 3) Studi Kasus: Peran Traversal dalam Aplikasi

Traversal adalah cara untuk **mengambil data dari BST** dengan urutan tertentu.

### A) Tampilan Utama (Default): Data Terurut – *InOrder Traversal*
- Pada tampilan utama, tabel pasien **selalu ditampilkan terurut berdasarkan ID Pasien**.
- Hal ini dilakukan dengan menggunakan **InOrder Traversal** pada BST.

**Manfaat bagi pengguna:**  
Petugas langsung melihat data pasien dari ID terkecil ke terbesar tanpa perlu mengurutkan secara manual.

---

### B) Mode Analisis (Opsional): Traversal Struktur BST
Aplikasi menyediakan fitur tambahan **Mode Tampilan**:
- **Tampilan Data: Terurut (Default)**  
  → Menggunakan InOrder Traversal
- **Tampilan Data: Analisis Struktur**  
  → Menyediakan pilihan:
    - **Urutan Kunjungan Node (PreOrder)**
    - **Urutan Kunjungan Node (PostOrder)**

**Tujuan Mode Analisis:**  
Mode ini digunakan untuk keperluan **pembelajaran dan analisis struktur data**, agar terlihat bahwa traversal yang berbeda akan menghasilkan urutan data yang berbeda pula.

> Traversal tidak ditampilkan secara teknis sebagai “BST Mode” pada UI utama agar tetap user-friendly,  
> namun tetap tersedia untuk kebutuhan akademik.

---

## 4) Cara Penggunaan Aplikasi

### A) Menambah Pasien (➕ Tambah)
1. Isi form pada panel kiri:
    - ID Pasien (angka, harus unik)
    - Nama
    - Umur
    - Gender (dropdown)
    - Diagnosis
    - Telepon
    - Alamat
2. Klik tombol **➕ Tambah**
3. Jika berhasil:
    - Data pasien akan muncul di tabel
    - Tabel otomatis terurut berdasarkan ID
4. Jika ID sudah ada:
    - Aplikasi menampilkan pesan error

---

### B) Mencari Pasien (🔍 Cari)
1. Masukkan ID Pasien pada kolom **Cari ID Pasien** di bagian atas
2. Klik tombol **🔍 Cari**
3. Hasil:
    - Baris pasien akan di-highlight pada tabel
    - Detail pasien ditampilkan dalam dialog

---

### C) Melihat Detail Pasien
1. **Double click** pada salah satu baris di tabel
2. Akan muncul dialog **Detail Pasien** berisi:
    - ID, Nama, Umur, Gender
    - Diagnosis, Telepon, Alamat

---

### D) Menghapus Pasien (🗑️ Hapus)
1. **Klik salah satu baris pasien di tabel** (pilih pasien)
2. Klik tombol **🗑️ Hapus**
3. Aplikasi akan menampilkan dialog konfirmasi
4. Jika dikonfirmasi:
    - Data pasien dihapus dari BST
    - Tabel otomatis diperbarui

> Jika belum memilih pasien di tabel, aplikasi akan menampilkan peringatan:  
> **“Pilih salah satu pasien di tabel terlebih dahulu.”**

---

### E) Reset Form (♻️ Reset Form)
- Mengosongkan seluruh input pada form pasien
- Digunakan untuk memulai input data baru

---

### F) Isi Contoh Data (🧪 Isi Contoh Data)
- Menambahkan **10–15 data pasien contoh** dengan ID acak
- Digunakan untuk menunjukkan bahwa:
    - Data masuk secara acak
    - Tetapi tetap ditampilkan **terurut otomatis** berkat BST

---

### G) Export CSV (⬇ Export CSV)
- Menyimpan data pasien ke file `patients.csv`
- Data yang diexport adalah **data terurut berdasarkan ID**

---

## 5) Gambaran Antarmuka (UI)
- **Bagian Atas:**  
  Judul aplikasi, Search bar, dan Mode Tampilan
- **Bagian Kiri:**  
  Form input data pasien
- **Bagian Kanan:**  
  Tabel daftar pasien (dengan zebra row)
- **Bagian Bawah:**  
  Tombol aksi (Tambah, Hapus, Reset, Seed, Export)

---

## 6) Cara Menjalankan Aplikasi

### A) Menggunakan IntelliJ IDEA
1. Buka project di IntelliJ IDEA
2. Jalankan file `App.java`

### B) Menggunakan Terminal (javac / java)
Dari root project:
```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out org.example.hospitalbst.App
