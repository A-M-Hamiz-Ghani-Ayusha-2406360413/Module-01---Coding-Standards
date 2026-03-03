# MODULE 01

## Refleksi 1
Pada pengembangan fitur Create, List, Edit, dan Delete Product, saya menerapkan prinsip Clean Code dan arsitektur MVC dengan memisahkan aplikasi menjadi Controller, Service, Repository, dan Model. Pemisahan ini membuat setiap layer memiliki tanggung jawab yang jelas sehingga kode lebih modular, mudah dipahami, dan mudah dirawat.

Saya menggunakan penamaan yang deskriptif serta method yang kecil dan fokus pada satu tugas agar meningkatkan readability dan maintainability. Setiap class mengikuti Single Responsibility Principle sehingga tidak terjadi pencampuran logika.

Dari sisi keamanan, productId tidak lagi diinput oleh pengguna, tetapi di-generate otomatis oleh sistem menggunakan UUID. Hal ini mencegah duplikasi dan manipulasi data. ID tetap ditampilkan kepada pengguna sebagai informasi, namun tidak dapat diubah.

Selain itu, saya menggunakan feature branch pada Git untuk mengembangkan setiap fitur secara terpisah agar perubahan lebih terkontrol dan meminimalkan konflik.

Secara keseluruhan, penerapan Clean Code dan secure coding membuat aplikasi lebih terstruktur, aman, dan scalable untuk pengembangan selanjutnya.

## Refleksi 2

Pada tahap ini, saya menambahkan pengujian untuk fitur Edit, Delete, dan Create Product menggunakan dua pendekatan, yaitu unit testing dan functional testing. Unit test digunakan untuk menguji logika bisnis pada level service dan repository, sedangkan functional test digunakan untuk mensimulasikan interaksi pengguna secara langsung melalui browser.

Untuk unit testing, saya menguji fitur Edit dan Delete pada ProductService. Pengujian dilakukan untuk skenario positif dan negatif. Pada skenario positif, produk berhasil diperbarui atau dihapus sesuai harapan. Pada skenario negatif, sistem menangani kasus ketika produk tidak ditemukan tanpa menyebabkan error atau crash. Pendekatan ini memastikan bahwa setiap method bekerja dengan benar secara terisolasi tanpa bergantung pada UI atau framework eksternal.

Untuk functional testing, saya menggunakan Selenium untuk mensimulasikan perilaku pengguna nyata, seperti membuka halaman, mengisi form, menekan tombol submit, dan memverifikasi hasil pada halaman daftar produk. Dengan cara ini, saya dapat memastikan integrasi antara controller, service, repository, template HTML, dan routing berjalan dengan baik secara end-to-end. Functional test membantu memvalidasi bahwa fitur benar-benar berfungsi dari perspektif pengguna, bukan hanya dari sisi logika program.

Saya juga menerapkan praktik clean architecture dengan memisahkan tanggung jawab antar layer sehingga unit test dapat dijalankan tanpa Spring context. Constructor injection digunakan agar dependency mudah diuji. Selain itu, ID produk di-generate oleh sistem untuk menjaga integritas data dan mencegah manipulasi oleh pengguna.

Secara keseluruhan, kombinasi unit test dan functional test meningkatkan kepercayaan terhadap kualitas aplikasi. Unit test memberikan kecepatan dan ketepatan dalam menguji logika kecil, sedangkan functional test memastikan seluruh sistem bekerja secara menyeluruh. Pendekatan ini membuat aplikasi lebih robust, aman, dan mudah dikembangkan di masa depan.

# MODULE 02
## 🚀 Deployment Information

**Application URL:** [https://eshop-app-hr5q.onrender.com](https://eshop-app-hr5q.onrender.com)

**Platform:** Render (Pull-based Deployment)

**Deployment Status:** ✅ Live

## 📊 Code Coverage Achievement

Pada modul ini, saya berhasil mencapai **100% code coverage** untuk semua komponen aplikasi dalam rangka implementasi BONUS

| Metric | Coverage |
|--------|----------|
| **Instructions** | 195/195 (100%) |
| **Branches** | 8/8 (100%) |
| **Lines** | 48/48 (100%) |
| **Methods** | 23/23 (100%) |
| **Classes** | 5/5 (100%) |

## 📝 Reflection Questions

### 1. Code Quality Issues Fixed

Selama mengerjakan exercise Module 2, beberapa code quality issue yang ditemukan dan diperbaiki adalah:

#### Issue 1: PMD Configuration Compatibility
**Masalah:** 
- Build gagal di GitHub Actions dengan error "Expression 'pmd' cannot be invoked as a function" pada Gradle 8.14
- Error disebabkan oleh syntax PMD configuration yang tidak kompatibel dengan versi Gradle terbaru

**Strategi Perbaikan:**
- Menambahkan proper imports: `import org.gradle.api.plugins.quality.PmdExtension`
- Mengubah syntax konfigurasi dari `pmd { }` menjadi `configure<PmdExtension> { }`
- Memastikan plugin `pmd` terdaftar di plugins block
- Hasil: Build berhasil dan PMD scanning dapat berjalan dengan baik di CI/CD pipeline

#### Issue 2: Dockerfile Permission Issue
**Masalah:**
- Docker build gagal dengan error "Permission denied: ./gradlew" saat deployment ke Render
- Alpine Linux image tidak secara otomatis memberikan execute permission pada script

**Strategi Perbaikan:**
- Menambahkan command `RUN chmod +x gradlew` sebelum menjalankan build
- Menggunakan multi-stage Docker build untuk optimasi image size
- Mengimplementasikan security best practices dengan non-root user
- Hasil: Docker image berhasil dibangun dan aplikasi ter-deploy dengan sukses

#### Issue 3: Security Enhancement in Dockerfile
**Masalah:**
- Dockerfile awal menjalankan aplikasi sebagai root user, yang merupakan security risk

**Strategi Perbaikan:**
- Membuat dedicated user `advshop` dengan UID/GID spesifik
- Menjalankan aplikasi dengan non-root user untuk meningkatkan keamanan
- Set proper file ownership dengan `--chown` flag pada copy command
- Hasil: Aplikasi berjalan dengan privilege terbatas, mengurangi attack surface

### 2. CI/CD Implementation Analysis

**Apakah implementasi CI/CD saat ini sudah memenuhi definisi Continuous Integration dan Continuous Deployment?**

**Jawaban: Ya, implementasi current sudah memenuhi definisi CI/CD dengan baik.**

#### Continuous Integration (CI)

Implementasi saya memenuhi prinsip CI karena:

1. **Automated Testing:** Setiap push ke repository secara otomatis men-trigger workflow CI yang menjalankan test suite lengkap. Workflow `ci.yml` menjalankan unit tests dan menghasilkan code coverage report menggunakan JaCoCo. Ini memastikan setiap perubahan kode diverifikasi secara otomatis sebelum di-merge.

2. **Code Quality Analysis:** Workflow `pmd-scanning.yml` berjalan otomatis pada setiap push ke semua branch, melakukan static code analysis untuk mengidentifikasi potential bugs, code smells, dan violations terhadap coding standards. PMD report di-upload sebagai artifacts untuk review, memastikan code quality tetap terjaga.

3. **Fast Feedback Loop:** Kedua workflow berjalan secara paralel dan memberikan feedback dalam hitungan menit. Developer dapat segera mengetahui jika ada test yang gagal atau code quality issue yang perlu diperbaiki, memungkinkan quick iteration dan early bug detection.

4. **Branch Protection (Implicit):** Dengan adanya automated checks pada setiap branch, tim dapat mengimplementasikan branch protection rules untuk memastikan hanya kode yang lulus tests dan quality checks yang dapat di-merge ke main branch.

#### Continuous Deployment (CD)

Implementasi saya memenuhi prinsip CD karena:

1. **Automated Deployment:** Menggunakan Render dengan pull-based deployment approach yang otomatis. File `render.yaml` dikonfigurasi dengan `autoDeploy: true` dan branch `master`, sehingga setiap push ke master branch secara otomatis men-trigger deployment process tanpa intervensi manual.

2. **Dockerized Application:** Aplikasi di-package sebagai Docker image dengan `Dockerfile` yang terdefinisi dengan baik. Render secara otomatis membangun Docker image, menjalankan tests, dan men-deploy container ke production environment. Multi-stage build memastikan image optimal dan secure.

3. **Zero-Downtime Deployment:** Render platform menyediakan health checks (`healthCheckPath: /`) yang memastikan aplikasi siap menerima traffic sebelum routing users ke instance baru. Ini meminimalkan downtime dan meningkatkan reliability.

4. **Environment Configuration:** Environment variables seperti `SPRING_PROFILES_ACTIVE=production` dan `SERVER_PORT=8080` dikonfigurasi melalui `render.yaml`, memastikan aplikasi berjalan dengan konfigurasi yang tepat untuk production environment.

5. **Monitoring dan Rollback Capability:** Render dashboard menyediakan logs real-time dan deployment history, memungkinkan monitoring aplikasi dan rollback cepat jika deployment mengalami masalah.

#### Kesimpulan

Implementasi CI/CD saya sudah memenuhi best practices modern software development. Pipeline CI memastikan code quality dan correctness melalui automated tests dan static analysis. Pipeline CD memastikan aplikasi yang sudah lolos verification langsung ter-deploy ke production secara otomatis. Kombinasi GitHub Actions untuk CI dan Render untuk CD menciptakan end-to-end automation dari commit hingga production, mempercepat delivery cycle dan mengurangi human error.

Yang membuat implementasi ini particularly effective adalah penggunaan multiple layers of checks (tests + code quality) di CI phase, dan reliable deployment platform (Render) yang handle infrastructure concerns di CD phase. Developer dapat fokus pada feature development dengan confidence bahwa code changes akan diverifikasi dan di-deploy secara konsisten dan reliable.

---

## 📂 Configuration Files

- **[render.yaml](render.yaml)** - Render deployment configuration (pull-based)
- **[Dockerfile](Dockerfile)** - Multi-stage Docker build with security best practices
- **[.dockerignore](.dockerignore)** - Docker build optimization
- **[.github/workflows/ci.yml](.github/workflows/ci.yml)** - Continuous Integration workflow
- **[.github/workflows/pmd-scanning.yml](.github/workflows/pmd-scanning.yml)** - Code quality analysis
- **[DEPLOYMENT.md](DEPLOYMENT.md)** - Comprehensive deployment guide

---

# MODULE 03 — SOLID Principles

## Reflection

### 1) Explain what principles you apply to your project!

Saya menerapkan kelima prinsip SOLID pada proyek ini:

#### Single Responsibility Principle (SRP)
Sebelumnya, file `ProductController.java` memuat dua class sekaligus: `ProductController` dan `CarController`. Ini melanggar SRP karena satu file bertanggung jawab atas dua resource yang berbeda (Product dan Car). Saya memisahkan `CarController` ke dalam file tersendiri (`CarController.java`) sehingga setiap class hanya memiliki satu tanggung jawab — menangani request untuk satu jenis entity saja.

#### Open/Closed Principle (OCP)
Arsitektur proyek sudah mengikuti OCP melalui penggunaan interface (`ProductService`, `CarService`). Jika di masa depan diperlukan service baru atau perubahan perilaku, kita cukup membuat implementasi baru dari interface tersebut tanpa memodifikasi kode yang sudah ada. Contohnya, jika ingin menambahkan caching pada `ProductService`, cukup buat `CachedProductServiceImpl` yang mengimplementasikan `ProductService` tanpa mengubah `ProductServiceImpl` yang sudah ada.

#### Liskov Substitution Principle (LSP)
Sebelumnya, `CarController extends ProductController`. Ini melanggar LSP karena `CarController` tidak dapat menggantikan `ProductController` secara semantik — Car bukan subtype dari Product. Method-method yang di-inherit dari `ProductController` (seperti `createProductPost`, `productListPage`) tidak relevan dan bisa menimbulkan perilaku yang tidak diharapkan jika `CarController` digunakan sebagai `ProductController`. Saya menghapus inheritance ini sehingga `CarController` menjadi class independen.

#### Interface Segregation Principle (ISP)
Sebelumnya, `ProductService` memiliki method `deleteProductById(String productId)` yang tidak diimplementasikan (melempar `UnsupportedOperationException`). Ini melanggar ISP karena client dipaksa bergantung pada method yang tidak mereka butuhkan. Saya menghapus method tersebut dari interface karena sudah ada method `delete(String id)` yang memenuhi kebutuhan yang sama. Interface `ProductService` dan `CarService` kini hanya berisi method-method yang benar-benar digunakan oleh client-nya.

#### Dependency Inversion Principle (DIP)
Sebelumnya, `CarController` bergantung langsung pada class konkret `CarServiceImpl` (bukan interface `CarService`), dan beberapa class menggunakan field injection (`@Autowired` pada field). Saya mengubah dependency `CarController` agar bergantung pada interface `CarService`, dan mengganti field injection menjadi constructor injection di `ProductController`, `CarController`, dan `CarServiceImpl`. Dengan constructor injection, dependency dinyatakan secara eksplisit dan high-level module bergantung pada abstraksi, bukan implementasi konkret.

### 2) Explain the advantages of applying SOLID principles to your project with examples.

Penerapan SOLID memberikan beberapa keuntungan nyata:

- **Maintainability yang lebih baik:** Dengan SRP, setiap class memiliki satu alasan untuk berubah. Misalnya, jika ada perubahan pada logika Car (menambah field `carYear`), kita hanya perlu mengubah `CarController.java`, `Car.java`, dan `CarServiceImpl.java` tanpa menyentuh file-file Product. Sebelum refactoring, perubahan pada `CarController` bisa saja secara tidak sengaja mempengaruhi `ProductController` karena keduanya berada dalam satu file dan memiliki hubungan inheritance.

- **Testability yang lebih mudah:** Dengan DIP dan constructor injection, kita bisa dengan mudah meng-inject mock object saat testing. Contohnya, `ProductController` menerima `ProductService` melalui constructor, sehingga pada unit test kita bisa menyuntikkan mock `ProductService` tanpa memerlukan Spring context. Ini membuat test lebih cepat dan terisolasi.

- **Extensibility tanpa risiko:** Dengan OCP, menambahkan fitur baru tidak memerlukan modifikasi kode yang sudah berjalan. Misalnya, jika ingin menambahkan entity baru seperti `Motorcycle`, kita cukup membuat `MotorcycleController`, `MotorcycleService`, `MotorcycleServiceImpl`, dan `MotorcycleRepository` tanpa mengubah kode Product atau Car yang sudah ada dan sudah teruji.

- **Keamanan dari bug inheritance:** Dengan LSP, menghapus inheritance `CarController extends ProductController` mencegah bug di mana endpoint Product secara tidak sengaja bisa diakses melalui path Car. Sebelumnya, semua method dari `ProductController` juga tersedia di `CarController`, yang bisa menyebabkan perilaku tidak terduga.

- **Interface yang bersih dan jelas:** Dengan ISP, menghapus `deleteProductById()` yang melempar `UnsupportedOperationException` membuat interface `ProductService` lebih jujur — setiap method yang dideklarasikan benar-benar memiliki implementasi yang fungsional. Developer baru yang membaca interface langsung tahu method apa saja yang tersedia dan berfungsi.

### 3) Explain the disadvantages of not applying SOLID principles to your project with examples.

Jika SOLID tidak diterapkan, proyek akan menghadapi beberapa masalah:

- **Tanpa SRP:** Jika `ProductController` dan `CarController` tetap dalam satu file, setiap perubahan pada salah satu controller berpotensi menimbulkan merge conflict saat beberapa developer bekerja bersamaan. File yang panjang juga lebih sulit di-review dan dipahami. Misalnya, file `ProductController.java` awalnya berisi 109 baris untuk dua class — jika kedua entity berkembang, file ini bisa menjadi ratusan baris yang sulit di-navigate.

- **Tanpa OCP:** Jika setiap penambahan fitur baru mengharuskan modifikasi class yang sudah ada, risiko memperkenalkan bug pada fitur yang sudah berjalan meningkat. Contohnya, jika logika `create` di `ProductServiceImpl` diubah untuk menambahkan validasi, perubahan tersebut bisa saja merusak flow yang sudah teruji dan bekerja di production.

- **Tanpa LSP:** Dengan `CarController extends ProductController`, semua endpoint Product (seperti `/product/create`, `/product/list`) secara implisit juga menjadi bagian dari `CarController`. Ini bisa menyebabkan bug di mana request ke `/car/create` justru memanggil method `createProductPost` dari parent class, menghasilkan perilaku yang salah dan membingungkan.

- **Tanpa ISP:** Method `deleteProductById()` yang melempar `UnsupportedOperationException` adalah "jebakan" bagi developer. Jika seseorang memanggil method ini karena melihatnya ada di interface, mereka akan mendapat runtime error yang seharusnya bisa dicegah pada compile time. Ini melanggar prinsip "fail fast" dan membuat debugging lebih sulit.

- **Tanpa DIP:** Jika `CarController` tetap bergantung pada `CarServiceImpl` (bukan `CarService` interface), maka: (1) unit testing menjadi sulit karena kita tidak bisa dengan mudah mengganti implementasi dengan mock, (2) jika di kemudian hari implementasi `CarService` diganti (misalnya dari in-memory ke database), semua class yang bergantung pada `CarServiceImpl` harus diubah. Dengan bergantung pada interface, pergantian implementasi cukup dilakukan di satu tempat (konfigurasi Spring) tanpa mengubah controller.