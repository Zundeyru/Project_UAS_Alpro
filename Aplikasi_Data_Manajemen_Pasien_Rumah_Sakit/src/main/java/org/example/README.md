# 🏥 Aplikasi Manajemen Data Pasien Rumah Sakit (Desktop Java Swing)

Aplikasi ini adalah program desktop sederhana untuk **mengelola data pasien rumah sakit** secara cepat dan rapi.  
Data pasien disimpan **di memori (in-memory)**, jadi **tidak memakai database**. Cocok untuk tugas struktur data dan demo aplikasi desktop.

---

## 1) Tujuan Aplikasi
Dengan aplikasi ini, petugas bisa:
- **Menambah** data pasien baru
- **Mencari** pasien berdasarkan **ID Pasien**
- **Menghapus** pasien berdasarkan **ID Pasien**
- Melihat daftar pasien **terurut berdasarkan ID** secara otomatis (default)

---

## 2) Studi Kasus: Kenapa Pakai Binary Search Tree (BST)?
**Masalah dunia nyata:**
Rumah sakit bisa punya **banyak pasien** (ratusan/ribuan). Petugas sering butuh:
- Mencari pasien berdasarkan **ID** dengan cepat
- Menambah pasien baru tanpa membuat data berantakan
- Menghapus pasien tertentu tanpa harus menyusun ulang data manual

**BST cocok karena:**
- Data disimpan berdasarkan **patientId** (angka, unik)
- Memudahkan:
    - **Insert** (Tambah pasien)
    - **Search** (Cari pasien)
    - **Delete** (Hapus pasien)
- Dan yang paling penting: dengan traversal tertentu, data bisa ditampilkan **rapi & terurut**.

> Di aplikasi ini, BST adalah “mesin” di belakang layar.  
> User tidak perlu paham BST untuk memakai aplikasi.

---

## 3) Studi Kasus: Di Mana Traversal Dipakai?
Traversal adalah cara “mengunjungi” data yang tersimpan di BST.

### ✅ Tampilan Utama (Default): Data Terurut (InOrder)
- Di UI utama, tabel pasien **selalu tampil terurut berdasarkan ID**.
- Ini terjadi karena aplikasi mengambil data menggunakan **InOrder Traversal**.

**Makna untuk user:**  
Petugas melihat daftar pasien rapi dari ID kecil ke besar tanpa perlu sort manual.

### 🧪 Mode Analisis (Opsional): Struktur Kunjungan Node
Aplikasi menyediakan dropdown kecil **Mode Tampilan**:
- **Tampilan Data: Terurut (Default)** → memakai InOrder (normal)
- **Tampilan Data: Analisis Struktur** → user bisa pilih:
    - **Urutan Kunjungan Node (PreOrder)**
    - **Urutan Kunjungan Node (PostOrder)**

**Makna untuk tugas struktur data:**  
Mode Analisis membantu menunjukkan bahwa data memang disimpan sebagai BST dan traversal bisa menghasilkan urutan berbeda.

> Di UI, traversal tidak ditulis “BST mode” agar user biasa tidak bingung.  
> Tapi untuk tugas, fitur ini tetap ada.

---

## 4) Cara Penggunaan Aplikasi

### A) Menambah Pasien (➕ Tambah)
1. Isi form di panel kiri:
    - ID Pasien (angka, unik)
    - Nama
    - Umur
    - Gender (combo)
    - Diagnosis
    - Telepon
    - Alamat
2. Klik **➕ Tambah**
3. Jika sukses → data muncul di tabel kanan (terurut otomatis)
4. Jika ID sudah ada → aplikasi menolak dan menampilkan pesan error

### B) Mencari Pasien (🔍 Cari)
1. Masukkan ID di bagian atas: **Cari ID Pasien**
2. Klik **🔍 Cari**
3. Hasil:
    - Baris pada tabel akan di-highlight (jika ada)
    - Detail pasien akan ditampilkan dalam dialog

### C) Melihat Detail Pasien (Double Click di tabel)
1. Double click salah satu baris pada tabel
2. Muncul popup **Detail Pasien** (ID, Nama, Umur, Gender, Diagnosis, Telepon, Alamat)

### D) Menghapus Pasien (🗑️ Hapus)
1. Masukkan ID pasien (biasanya dari form / atau search bar)
2. Klik **🗑️ Hapus**
3. Aplikasi akan meminta konfirmasi
4. Jika setuju → data dihapus dan tabel otomatis refresh

### E) Reset Form (♻️ Reset Form)
- Mengosongkan semua input di form kiri agar mudah input pasien baru

### F) Isi Contoh Data (🧪 Seed)
- Klik **🧪 Isi Contoh Data** untuk menambahkan 10–15 pasien contoh dengan ID acak
- Gunanya agar terlihat manfaat:
    - Data masuk acak
    - Tetapi tabel tetap tampil **terurut otomatis**

### G) Export CSV (⬇ Export CSV) (Jika digunakan)
- Menyimpan data pasien (versi terurut) ke file `patients.csv`

---

## 5) Gambaran UI (Tanpa Screenshot)
- **Atas:** Judul aplikasi + Search bar + Mode Tampilan
- **Kiri:** Form input pasien
- **Kanan:** Tabel daftar pasien (zebra row)
- **Bawah:** Tombol aksi (Tambah/Hapus/Reset/Seed/Export)

---

## 6) Cara Menjalankan
### IntelliJ IDEA
1. Buka project
2. Jalankan `App.java`

### Terminal (javac/java)
Dari root project:
```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out org.example.hospitalbst.App
