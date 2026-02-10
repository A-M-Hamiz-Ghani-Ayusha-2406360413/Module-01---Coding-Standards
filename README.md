# REFLEKSI 1
Pada pengembangan fitur Create, List, Edit, dan Delete Product, saya menerapkan prinsip Clean Code dan arsitektur MVC dengan memisahkan aplikasi menjadi Controller, Service, Repository, dan Model. Pemisahan ini membuat setiap layer memiliki tanggung jawab yang jelas sehingga kode lebih modular, mudah dipahami, dan mudah dirawat.

Saya menggunakan penamaan yang deskriptif serta method yang kecil dan fokus pada satu tugas agar meningkatkan readability dan maintainability. Setiap class mengikuti Single Responsibility Principle sehingga tidak terjadi pencampuran logika.

Dari sisi keamanan, productId tidak lagi diinput oleh pengguna, tetapi di-generate otomatis oleh sistem menggunakan UUID. Hal ini mencegah duplikasi dan manipulasi data. ID tetap ditampilkan kepada pengguna sebagai informasi, namun tidak dapat diubah.

Selain itu, saya menggunakan feature branch pada Git untuk mengembangkan setiap fitur secara terpisah agar perubahan lebih terkontrol dan meminimalkan konflik.

Secara keseluruhan, penerapan Clean Code dan secure coding membuat aplikasi lebih terstruktur, aman, dan scalable untuk pengembangan selanjutnya.

# REFLEKSI 2

Pada tahap ini, saya menambahkan pengujian untuk fitur Edit, Delete, dan Create Product menggunakan dua pendekatan, yaitu unit testing dan functional testing. Unit test digunakan untuk menguji logika bisnis pada level service dan repository, sedangkan functional test digunakan untuk mensimulasikan interaksi pengguna secara langsung melalui browser.

Untuk unit testing, saya menguji fitur Edit dan Delete pada ProductService. Pengujian dilakukan untuk skenario positif dan negatif. Pada skenario positif, produk berhasil diperbarui atau dihapus sesuai harapan. Pada skenario negatif, sistem menangani kasus ketika produk tidak ditemukan tanpa menyebabkan error atau crash. Pendekatan ini memastikan bahwa setiap method bekerja dengan benar secara terisolasi tanpa bergantung pada UI atau framework eksternal.

Untuk functional testing, saya menggunakan Selenium untuk mensimulasikan perilaku pengguna nyata, seperti membuka halaman, mengisi form, menekan tombol submit, dan memverifikasi hasil pada halaman daftar produk. Dengan cara ini, saya dapat memastikan integrasi antara controller, service, repository, template HTML, dan routing berjalan dengan baik secara end-to-end. Functional test membantu memvalidasi bahwa fitur benar-benar berfungsi dari perspektif pengguna, bukan hanya dari sisi logika program.

Saya juga menerapkan praktik clean architecture dengan memisahkan tanggung jawab antar layer sehingga unit test dapat dijalankan tanpa Spring context. Constructor injection digunakan agar dependency mudah diuji. Selain itu, ID produk di-generate oleh sistem untuk menjaga integritas data dan mencegah manipulasi oleh pengguna.

Secara keseluruhan, kombinasi unit test dan functional test meningkatkan kepercayaan terhadap kualitas aplikasi. Unit test memberikan kecepatan dan ketepatan dalam menguji logika kecil, sedangkan functional test memastikan seluruh sistem bekerja secara menyeluruh. Pendekatan ini membuat aplikasi lebih robust, aman, dan mudah dikembangkan di masa depan.
