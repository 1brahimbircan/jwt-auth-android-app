
# Kotlin MVVM JWT Authentication

![Uygulama Ekran Görüntüsü](https://raw.githubusercontent.com/1brahimbircan/jwt-auth-android-app/master/app-image.jpg)

Bu proje, JWT (JSON Web Token) kullanarak oturum işlemlerini yönetebileceğiniz bir Kotlin uygulamasıdır. MVVM (Model-View-ViewModel) mimarisine uygun olarak geliştirilmiştir. Kullanıcılar kayıt ve giriş işlemlerini gerçekleştirebilir ve güvenli bir şekilde oturumlarını yönetebilirler.

## Proje Özeti
Bu projede, kullanıcıların kayıt olma, giriş yapma ve oturumlarını yönetme işlemleri, PHP ile geliştirilmiş bir backend API kullanılarak gerçekleştirilmiştir. JWT, kimlik doğrulama ve yetkilendirme işlemleri için güvenli bir yöntem olarak kullanılmaktadır.

## Teknolojiler ve Araçlar
* Kotlin: Android uygulama geliştirme dili.
* MVVM: Model-View-ViewModel mimarisi.
* JWT: JSON Web Token, güvenli kimlik doğrulama ve yetkilendirme sağlar.
* PHP: Sunucu tarafında JWT ile kimlik doğrulama işlemlerini gerçekleştirmek için kullanılan dil.
* Retrofit: HTTP istekleri için kullanılan kütüphane.
* LiveData ve ViewModel: MVVM mimarisinde veri yönetimi ve UI güncellemeleri için kullanılır.
## Özellikler
* Kullanıcı Kaydı: Yeni kullanıcıların sisteme kayıt olmasını sağlar.
* Kullanıcı Girişi: Kayıtlı kullanıcıların sisteme giriş yapmasını sağlar.
* Güvenli Oturum Yönetimi: JWT kullanarak güvenli oturum yönetimi sağlar.
* MVVM Mimarisi: Modern yazılım geliştirme prensiplerine uygun yapı.
* SharedPreferences: JWT token'larını güvenli bir şekilde saklamak için kullanılır.

## Kurulum ve Kullanım
### Backend Kurulumu

* jwt-auth-api-with-php deposunu klonlayın.

```bash
  git clone https://github.com/1brahimbircan/jwt-auth-api-with-php.git

```
* API ayarlarını ve gerekli MYSQL yapılandırmalarını yapın ve sunucuyu başlatın.

### Android Uygulaması Kurulumu
* jwt-auth-android-app deposunu klonlayın.
```bash
  git clone https://github.com/1brahimbircan/jwt-auth-android-app.git

```
* Android Studio'yu açın ve bu projeyi import edin.
* build.gradle dosyasındaki bağımlılıkları kontrol edin ve gerekli kütüphaneleri yükleyin.
* Retrofit ve ViewModel yapılandırmalarını yapın.
* Backend API URL'lerini uygulamanıza uygun şekilde yapılandırın.

### Kullanım
* Uygulamayı çalıştırın.
* Kayıt ekranından yeni bir kullanıcı oluşturun.
* Giriş ekranından oluşturduğunuz kullanıcı ile sisteme giriş yapın.
* JWT token'ı kullanarak güvenli bir şekilde oturum işlemlerini yönetin.

## Proje Yapısı

```
- api
- di
- models
- repository
- ui
  - fragments
  - viewmodels
- utils

```
## Katkıda Bulunma
Katkıda bulunmak isterseniz, lütfen bir pull request gönderin veya bir issue açın.

## Lisans
Bu proje MIT Lisansı ile lisanslanmıştır. Daha fazla bilgi için LICENSE dosyasına bakın.

## İletişim
Herhangi bir sorunuz veya geri bildiriminiz için lütfen coder.ibrahimbircan@gmail.com adresine e-posta gönderin.
