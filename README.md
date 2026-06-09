# 🛒 سوبرماركت — SuperMarket POS
### نظام إدارة سوبرماركت متكامل | Kotlin + Jetpack Compose + Firebase

> تطوير: **كلود** • بواسطة **محمد الصارم**

---

## 📱 مميزات التطبيق

### 🎨 التصميم
- واجهة داكنة احترافية بألوان أخضر/نيلي
- **قائمة جانبية (Drawer)** للتنقل بين الأقسام
- لوحة تحكم (Dashboard) بإحصائيات لحظية
- رسوم بيانية للمبيعات الأسبوعية
- رموز تعبيرية لكل فئة منتجات

### 🔧 الوظائف الرئيسية
| القسم | الوظيفة |
|-------|---------|
| نقطة البيع | بيع سريع بالباركود + خصومات + طرق دفع متعددة |
| المخزون | إدارة كاملة مع تنبيه انتهاء الصلاحية |
| التقارير | مبيعات يومية/أسبوعية/شهرية/سنوية + رسوم بيانية |
| العملاء | سجل عملاء + نقاط ولاء + مستويات عضوية |
| المشتريات | سجل المشتريات من الموردين |
| المصروفات | تتبع المصروفات بالفئات |
| المستخدمون | صلاحيات 4 مستويات |
| الإشعارات | تنبيهات مبيعات ونفاد مخزون فورية |

---

## 🔐 بيانات الدخول

| الحقل | القيمة |
|-------|--------|
| اسم المستخدم | `Mohali` |
| كلمة المرور | `1234567` |
| الصلاحية | مدير (Admin) |

---

## ⚙️ خطوات الإعداد

### 1. إعداد Firebase
```
1. اذهب إلى https://console.firebase.google.com
2. أنشئ مشروع باسم: SuperMarket
3. أضف تطبيق Android - Package: com.supermarket.app
4. حمّل google-services.json → ضعه في مجلد app/
5. فعّل: Authentication (Email/Password) + Firestore + FCM
```

### 2. Firestore Rules
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

---

## 🚀 رفع على GitHub

```bash
git init
git add .
git commit -m "Initial commit - SuperMarket v1.0.0"
git remote add origin https://github.com/USERNAME/SuperMarket.git
git branch -M main
git push -u origin main
```

---

## 🏗️ هيكل المشروع
```
app/src/main/java/com/supermarket/app/
├── data/
│   ├── models/Models.kt          ← نماذج البيانات
│   ├── local/Database.kt         ← Room DB + DAOs
│   └── remote/FirebaseRepository ← Firebase
├── di/AppModule.kt               ← Hilt DI
├── notifications/                ← FCM Service
├── ui/
│   ├── home/
│   │   ├── MainScreen.kt         ← الشاشة الرئيسية + القائمة الجانبية
│   │   ├── DashboardScreen.kt    ← لوحة التحكم
│   │   └── HomeViewModel.kt
│   ├── login/                    ← شاشة الدخول
│   ├── inventory/                ← إدارة المخزون
│   ├── sales/                    ← نقطة البيع + الفواتير
│   ├── reports/                  ← التقارير
│   ├── customers/                ← العملاء
│   ├── purchases/                ← المشتريات
│   ├── expenses/                 ← المصروفات
│   ├── users/                    ← المستخدمون
│   ├── settings/                 ← الإعدادات
│   ├── notifications/            ← الإشعارات
│   ├── components/               ← مكونات مشتركة
│   ├── navigation/Navigation.kt  ← التنقل
│   └── theme/Theme.kt            ← الثيم والألوان
└── utils/PrefsManager.kt         ← تفضيلات المستخدم
```

---

## 🎨 لوحة الألوان
| الاسم | اللون |
|-------|-------|
| خلفية رئيسية | `#0A1628` |
| أخضر رئيسي | `#00D26A` |
| برتقالي | `#FF6B2B` |
| سماوي | `#00E5FF` |
| أصفر | `#FFD60A` |
| أحمر | `#FF4757` |

---

## 📦 التقنيات
- **Kotlin** + **Jetpack Compose** + **Material 3**
- **Firebase** (Auth + Firestore + FCM)
- **Room Database** (offline support)
- **Hilt** (Dependency Injection)
- **Navigation Compose** + **Drawer**
- **Coroutines + StateFlow**

---

*تطوير: كلود • بواسطة محمد الصارم © 2024*
# Supermarket
