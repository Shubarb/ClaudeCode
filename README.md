# Emoji Battery

Ứng dụng Android (Kotlin) cho phép tuỳ biến status bar với emoji và icon pin.

## Mở project

Mở thư mục gốc bằng Android Studio (Ladybug trở lên), chờ Gradle sync rồi chạy cấu hình `app`.
Yêu cầu JDK 17+, compileSdk 35, minSdk 26.

## Kiến trúc

Clean Architecture (`core` / `domain` / `data` / `presentation`) + MVI, UI bằng XML View + ViewBinding,
Fragment + Navigation Component, Hilt, Coroutines/Flow, DataStore. Quy ước chi tiết và cách thêm màn mới:
xem [CLAUDE.md](CLAUDE.md).
