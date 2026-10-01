# GölköyHaber Android APK

GölköyHaber'in bağımsız Android uygulama projesidir.

## GitHub APK derleme

1. Repository köküne bu paketin içindeki dosyaları yükleyin.
2. GitHub'da **Actions** sekmesine girin.
3. **GolkoyHaber APK** workflow'unu seçin.
4. **Run workflow** ile çalıştırın.
5. Oluşan `GolkoyHaber-debug` artifact'ından APK'yı indirin.

## Mevcut yapı

- Uygulama adı: GölköyHaber
- Application ID: `com.golkoyhaber.app`
- Launcher ikonu: GölköyHaber + yeşil fındık logosu
- Facebook yayın hedefi: `https://www.facebook.com/habergolkoy52`
- Kategoriler: Gölköy, Resmî, Asayiş, Eğitim, Sağlık, Spor, Tarım, Siyaset, Hava, Vefat

## Mimari yön

Kaynaklar → Haber Motoru → Filtre/Mükerrer Kontrol → Editör Onayı → APK + Web + Facebook Sayfası

Facebook erişim tokenları APK içine konulmamalıdır. Otomatik Facebook yayınlama backend üzerinden yapılacaktır.
