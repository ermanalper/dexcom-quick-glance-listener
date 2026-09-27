# Dexcom Quick Glance Listener

*Türkçe versiyonu için aşağı kaydırın*

An Android application that acts as a push client for glucose monitoring systems. It listens to the **Dexcom Follow** app's "Quick Glance" notification, extracts the glucose value, and pushes it to a custom backend webhook.

## ⚙️ Installation and Build

1. Clone or download this repository.
2. Find the `local.properties.example` file in the root directory.
3. Copy it or rename it to `local.properties`.
4. Open `local.properties` and fill in the required variables (like your backend API URL).
5. Build the project via Android Studio and install the APK on your device.

## 🚀 Setup and Usage

1. **Enable Dexcom Quick Glance:** Open your official Dexcom Follow app, go to settings, and turn on the **Quick Glance** feature. A persistent notification showing your glucose should appear.
2. **Grant Notification Access:** Open this app and grant it the required **Notification Access** permission so it can read the Dexcom notification.
3. **Disable Battery Optimization:** To prevent the Android operating system from killing the background listener service, it is highly recommended to disable power saving/battery optimization for this app in your phone's settings.

## ⚠️ Important Disclaimer & Known Issues

**Do not rely on this app for critical medical decisions.** 
There is a known issue originating from the official Dexcom Follow app: when the Android device stays in a deep sleep state for a long period, Dexcom Follow sometimes fails to update the Quick Glance notification. If the notification itself is frozen, this listener app will not be able to push new data to your backend. 

---
---

# Dexcom Quick Glance Listener (Türkçe)

Glukoz takip sistemleri için bir "push client" görevi gören Android uygulamasıdır. **Dexcom Follow** uygulamasının "Quick Glance" (Hızlı Bakış) bildirimini dinler, içerisindeki glukoz değerini ayrıştırır ve kendi backend webhook'unuza iletir.

## ⚙️ Kurulum ve Derleme (Build)

1. Repoyu bilgisayarınıza indirin (clone).
2. Ana dizinde bulunan `local.properties.example` dosyasının adını `local.properties` olarak değiştirin (veya kopyalayın).
3. `local.properties` dosyasının içindeki gerekli değişkenleri (backend API URL'niz gibi) kendi sisteminize göre doldurun.
4. Projeyi Android Studio üzerinden derleyin (build) ve APK'yı telefonunuza kurun.

## 🚀 Ayarlar ve Kullanım

1. **Quick Glance Ayarını Açın:** Orijinal Dexcom Follow uygulamasını açın, ayarlara gidin ve **Quick Glance** özelliğini aktif hale getirin. Telefonunuzun bildirim çubuğunda glukoz değerinizi gösteren sabit bir bildirim belirmelidir.
2. **Bildirim Okuma İzni Verin:** Kurduğunuz bu uygulamayı başlatın ve Dexcom bildirimlerini okuyabilmesi için **Bildirim Okuma İzni (Notification Access)** verin.
3. **Güç Tasarrufunu Kapatın:** Arka planda çalışan dinleme servisinin Android işletim sistemi tarafından uyutulmaması/kapatılmaması için, telefonunuzun ayarlarından bu uygulama için pil optimizasyonu (güç tasarrufu) kısıtlamalarını kaldırmanız şiddetle tavsiye edilir.

## ⚠️ Önemli Uyarı ve Bilinen Sorunlar

**Bu uygulamaya hayati tıbbi kararlarınızda veya kritik alarmlarda %100 güvenmeyin.**
Orijinal Dexcom Follow uygulamasından kaynaklı bilinen bir sorun bulunmaktadır: Android cihaz uzun süre uykuda (deep sleep) kaldığında, Dexcom Follow uygulaması Quick Glance bildirimini güncelleyemeyebiliyor (bildirim donuyor). Eğer bildirimdeki değer güncellenmezse, bu dinleyici uygulama da backend'inize yeni veri iletemez.
