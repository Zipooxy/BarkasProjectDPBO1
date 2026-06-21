# Barkas Project DPBO — CI/CD Pipeline

Aplikasi marketplace berbasis terminal (Java) hasil Tugas Besar Dasar Pemrograman
Berorientasi Objek (DPBO), yang pada repository ini dilengkapi dengan **pipeline
CI/CD penuh** menggunakan GitHub Actions untuk mata kuliah **Manajemen Konfigurasi
dan Evolusi Perangkat Lunak**.

Fitur aplikasi: registrasi & login user, jual/beli produk (kategori Fashion,
Electronic, Living), saldo & status member (diskon 10%), serta riwayat transaksi.

## Arsitektur Pipeline CI/CD

Seluruh pipeline didefinisikan dalam satu workflow file:
[`.github/workflows/ci-cd.yml`](.github/workflows/ci-cd.yml), terdiri dari 5 job
yang berjalan berurutan (`needs`) dan masing-masing merepresentasikan satu
komponen wajib pada tugas besar:

```
push/PR  ──▶  build  ──▶  test  ──▶  inspect  ──▶  deploy-staging  ──▶  deploy-production
            (CI)        (CT)     (Inspection)    (CD - otomatis)    (CD - approval manual)
```

| Job | Komponen | Apa yang dilakukan |
|---|---|---|
| `build` | Continuous Integration | `mvn compile`: compile project & resolve dependency Maven setiap push/PR |
| `test` | Continuous Testing | `mvn test`: jalankan unit test JUnit 5, upload laporan Surefire & JaCoCo, publish hasil ke tab Actions |
| `inspect` | Continuous Inspection | `mvn verify` + SonarCloud scan, gagal otomatis jika Quality Gate merah |
| `deploy-staging` | Continuous Delivery (otomatis) | Build jar & publish sebagai GitHub Pre-Release (environment `staging`) |
| `deploy-production` | Continuous Delivery (approval manual) | Menunggu approval reviewer pada environment `production`, lalu publish GitHub Release |

### Strategi Branching & Trigger

- **Branch `dev` / `feature/**`**: setiap `push` atau `pull request` ke branch ini
  menjalankan job `build`, `test`, dan `inspect` (CI + CT + Continuous Inspection).
- **Branch `main`**: hasil merge dari `dev`/`feature/**`. Push ke `main` menjalankan
  seluruh pipeline termasuk `deploy-staging` dan `deploy-production` (CD).
- **Pilihan CD**: kelompok memilih **Continuous Delivery** (bukan Continuous
  Deployment penuh) — deploy ke *staging* berjalan otomatis, tetapi rilis ke
  *production* **membutuhkan approval manual** melalui fitur GitHub Environments.
- `workflow_dispatch` ditambahkan agar pipeline juga bisa dijalankan manual,
  memudahkan demonstrasi pada video.

### Environment Variable & Secrets yang Digunakan

| Nama | Jenis | Digunakan di job | Keterangan |
|---|---|---|---|
| `SONAR_TOKEN` | Secret | `inspect` | Token autentikasi ke SonarCloud |
| `SONAR_PROJECT_KEY` | Variable | `inspect` | Project key SonarCloud |
| `SONAR_ORGANIZATION` | Variable | `inspect` | Organization key SonarCloud |
| `GITHUB_TOKEN` | Bawaan GitHub Actions | `inspect`, `deploy-staging`, `deploy-production` | Untuk PR decoration Sonar & membuat GitHub Release, tidak perlu dibuat manual |

Tidak ada secret yang ditulis langsung di dalam kode/workflow — semua diakses
lewat `${{ secrets.* }}` / `${{ vars.* }}` agar aman.

## Daftar Tools & Teknologi per Tahap Pipeline

| Tahap | Tools/Teknologi |
|---|---|
| Bahasa & build tool | Java 21, Apache Maven |
| Continuous Integration | GitHub Actions, `actions/setup-java`, Maven dependency cache |
| Continuous Testing | JUnit 5 (Jupiter), Maven Surefire Plugin, `dorny/test-reporter` |
| Continuous Inspection | SonarCloud, JaCoCo (code coverage), `sonar-maven-plugin` |
| Continuous Delivery | Maven Jar Plugin, GitHub Releases, GitHub Environments, `softprops/action-gh-release` |

## Tabel Pembagian Tugas Anggota Kelompok

> **Catatan:** isi tabel ini sesuai pembagian tugas kelompok kalian sebelum
> dikumpulkan. Setiap anggota wajib tampil di video menjelaskan bagian yang
> menjadi tanggung jawabnya (lihat ketentuan video pada soal Tugas Besar).

| Nama | NIM | Komponen Pipeline yang Menjadi Tanggung Jawab |
|---|---|---|
| _Nama Anggota 1_ | _NIM_ | Continuous Integration (job `build`) & strategi branching |
| _Nama Anggota 2_ | _NIM_ | Continuous Testing (job `test`) & unit test JUnit 5 |
| _Nama Anggota 3_ | _NIM_ | Continuous Inspection (job `inspect`) & setup SonarCloud |
| _Nama Anggota 4_ | _NIM_ | Continuous Delivery (job `deploy-staging` & `deploy-production`) |

## Cara Menjalankan Proyek Secara Lokal

Prasyarat: JDK 21 dan Apache Maven sudah terpasang.

```bash
# 1. Clone repository
git clone <URL_REPOSITORY_KALIAN>
cd BarkasProjectDPBO

# 2. Compile project
mvn clean compile

# 3. Jalankan unit test
mvn test

# 4. Build jar yang bisa dijalankan
mvn package

# 5. Jalankan aplikasi
java -jar target/BarkasProjectDPBO-1.0-SNAPSHOT.jar
```

## Setup yang WAJIB Dilakukan Sebelum Pipeline Bisa Berjalan Penuh

Karena pipeline ini melibatkan layanan eksternal (SonarCloud) dan approval
manual (GitHub Environments), ada beberapa langkah satu-kali yang **wajib**
dilakukan oleh kelompok di repository GitHub-nya masing-masing:

### 1. Buat repository sebagai Public

Fitur "Required reviewers" pada GitHub Environments (dipakai untuk approval
manual sebelum deploy ke production) **hanya gratis untuk repository public**
pada akun GitHub Free/Pro/Team. Jadikan repository ini **public** agar fitur
approval manual berfungsi tanpa biaya tambahan.

### 2. Setup SonarCloud

1. Buka [sonarcloud.io](https://sonarcloud.io) dan login dengan akun GitHub.
2. Buat organization baru (jika belum ada), lalu klik **Analyze new project**
   dan pilih repository ini → catat **Organization Key** dan **Project Key**.
3. Pilih metode analisis **"With GitHub Actions"** lalu generate token pada
   **My Account > Security > Generate Token**.
4. Di repository GitHub: **Settings > Secrets and variables > Actions**:
   - Tab **Secrets** → New repository secret → `SONAR_TOKEN` → paste token.
   - Tab **Variables** → New repository variable →
     `SONAR_PROJECT_KEY` dan `SONAR_ORGANIZATION` sesuai langkah 2.
5. Di SonarCloud, set **Analysis Method** menjadi *CI-based* (bukan otomatis),
   agar tidak konflik dengan workflow GitHub Actions yang sudah dibuat.

### 3. Setup GitHub Environments (`staging` & `production`)

1. Buka **Settings > Environments** pada repository.
2. Klik **New environment**, beri nama `staging` → Configure environment
   (boleh tanpa protection rule, deploy otomatis).
3. Klik **New environment** lagi, beri nama `production` → Configure
   environment → centang **Required reviewers** → tambahkan minimal satu
   anggota kelompok sebagai reviewer → Save protection rules.
4. Setelah ini, job `deploy-production` akan **berhenti dan menunggu approval**
   dari reviewer yang ditentukan setiap kali ada push ke `main`. Approval bisa
   dilakukan di tab **Actions** pada run yang sedang menunggu.

### 4. (Opsional) Proteksi Branch `main`

Disarankan mengaktifkan **Settings > Branches > Branch protection rules**
untuk `main`, mewajibkan status check `build`, `test`, dan `inspect` lulus
sebelum PR bisa di-merge — ini memperkuat poin penilaian "strategi branching
dan proteksi branch diterapkan".

## Catatan Perubahan dari Kode Asli

Beberapa penyesuaian kecil dilakukan agar project dapat diuji dan di-pipeline-kan:

- **`pom.xml`**: menambahkan dependency JUnit 5, plugin Maven Surefire,
  Maven Jar Plugin (manifest `Main-Class`), dan JaCoCo. Properti
  `maven.compiler.release` diturunkan dari `23` ke `21` (LTS) agar lebih
  stabil dan konsisten dengan JDK yang umum tersedia di GitHub Actions runner.
- **`Service/UserService.java`**: memperbaiki bug logika validasi login —
  kondisi sebelumnya menggunakan `&&` (login baru ditolak jika username **dan**
  password **sama-sama** salah), seharusnya `||` (login ditolak jika salah
  satu saja yang salah). Unit test `UserServiceTest` menguji perilaku yang
  benar ini — ini juga contoh nyata bagaimana Continuous Testing membantu
  menangkap bug.
- Ditambahkan unit test (folder `src/test/java`) untuk package `Model`,
  `Service`, `Exception`, dan sebagian `Utils` (method yang tidak bergantung
  langsung pada interaksi konsol interaktif).

## Tentang Code Coverage & Kemungkinan Kasus Gagal pada Inspection

Method yang sangat interaktif (membaca input dari `Scanner`/konsol secara
langsung di tengah alur, seperti `Main.main`, `AppUtils.inputInt`,
`TransactionService.sellItem`, `UserService.inputLogin`/`inputDataRegistrasi`,
dan menu-menu di `UserService`/`ProductService`) **tidak diuji** karena
membutuhkan refactor pemisahan I/O yang di luar cakupan tugas ini.

Akibatnya, coverage keseluruhan project kemungkinan **di bawah 80%**. Pada
analisis SonarCloud pertama kali, seluruh kode dianggap "kode baru", sehingga
Quality Gate default (`Sonar way`, mensyaratkan coverage kode baru ≥ 80%)
**bisa gagal**. Ini bisa dimanfaatkan kelompok sebagai skenario **"kasus
gagal"** pada Bagian 2 video demonstrasi (jelaskan kenapa gagal: coverage
kurang). Jika ingin pipeline lolos, kelompok bisa menambah unit test lagi atau
menyesuaikan kondisi Quality Gate di SonarCloud.

## Cara Mendemonstrasikan Skenario Sukses & Gagal (untuk Video)

- **Kasus sukses**: push/merge kode yang seluruh test-nya lulus dan coverage
  mencukupi → seluruh job `build` → `test` → `inspect` hijau, lalu
  `deploy-staging`/`deploy-production` berjalan (production menunggu approval).
- **Kasus gagal (test)**: ubah salah satu assertion di file test (misalnya di
  `UserServiceTest`) menjadi nilai yang salah, lalu push ke `dev` → job `test`
  akan merah dan pipeline berhenti di situ.
- **Kasus gagal (inspection)**: bisa memanfaatkan kondisi coverage rendah di
  atas, atau sengaja menambahkan code smell (misalnya duplikasi kode atau
  variable yang tidak dipakai) lalu jelaskan hasil quality gate yang gagal
  di log SonarCloud/GitHub Actions.
