# REFLEKSI 1
Pada pengembangan fitur Create, List, Edit, dan Delete Product, saya menerapkan prinsip Clean Code dan arsitektur MVC dengan memisahkan aplikasi menjadi Controller, Service, Repository, dan Model. Pemisahan ini membuat setiap layer memiliki tanggung jawab yang jelas sehingga kode lebih modular, mudah dipahami, dan mudah dirawat.

Saya menggunakan penamaan yang deskriptif serta method yang kecil dan fokus pada satu tugas agar meningkatkan readability dan maintainability. Setiap class mengikuti Single Responsibility Principle sehingga tidak terjadi pencampuran logika.

Dari sisi keamanan, productId tidak lagi diinput oleh pengguna, tetapi di-generate otomatis oleh sistem menggunakan UUID. Hal ini mencegah duplikasi dan manipulasi data. ID tetap ditampilkan kepada pengguna sebagai informasi, namun tidak dapat diubah.

Selain itu, saya menggunakan feature branch pada Git untuk mengembangkan setiap fitur secara terpisah agar perubahan lebih terkontrol dan meminimalkan konflik.

Secara keseluruhan, penerapan Clean Code dan secure coding membuat aplikasi lebih terstruktur, aman, dan scalable untuk pengembangan selanjutnya.
