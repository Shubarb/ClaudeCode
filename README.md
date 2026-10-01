# Emoji Battery

Ứng dụng Android (Kotlin) cho phép tuỳ biến status bar với emoji và icon pin.

## Mở project

Mở thư mục gốc bằng Android Studio (Ladybug trở lên), chờ Gradle sync rồi chạy cấu hình `app`.
Yêu cầu JDK 17+, compileSdk 35, minSdk 26.

## Kiến trúc

Single module, chia package theo Clean Architecture, presentation theo MVI.

```
com.emojibattery.app
├── core
│   ├── mvi          # UiState / UiIntent / UiEffect, MviViewModel, CollectEffect
│   └── di           # Dispatcher qualifiers
├── domain           # thuần Kotlin, không phụ thuộc Android
│   ├── model
│   ├── repository   # interface
│   └── usecase
├── data
│   ├── local        # DataStore data source
│   ├── repository   # implementation của domain/repository
│   └── di           # Hilt modules (bind repository, provide DataStore)
└── presentation
    ├── navigation   # NavHost + routes
    ├── theme
    ├── splash       # SplashContract (State/Intent/Effect), ViewModel, Screen
    └── home
```

Luồng một màn hình theo MVI:

1. UI gửi `Intent` qua `viewModel.onIntent(...)`.
2. ViewModel gọi use case, cập nhật `State` bằng `setState { copy(...) }`.
3. UI render `state` (StateFlow) và nhận sự kiện một lần (điều hướng, toast) qua `effect`.

Thêm màn mới: tạo package trong `presentation/`, viết `XxxContract.kt`, `XxxViewModel : MviViewModel<...>`,
`XxxRoute` (stateful) + `XxxScreen` (stateless, có Preview), rồi thêm route vào `AppNavHost`.

## Tech stack

Kotlin 2.1, Jetpack Compose (Material 3), Hilt, Coroutines/Flow, Navigation Compose,
DataStore, AndroidX SplashScreen. Phiên bản thư viện nằm ở `gradle/libs.versions.toml`.
