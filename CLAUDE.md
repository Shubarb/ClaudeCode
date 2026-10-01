# CLAUDE.md

Hướng dẫn cho Claude (và dev) khi làm việc trong repo này. Đọc file này trước khi sửa code.

## Dự án

**Emoji Battery**: app Android (Kotlin) cho phép tuỳ biến status bar bằng emoji và icon pin.
Package: `com.emojibattery.app`. Single module `:app`. minSdk 26, compile/targetSdk 35, JDK 17.
Thiết kế: Figma file `5NxsPCisNip42YZgYovVaf` (Emoji-Battery), màn Splash ở node `3-435`.

## Quy tắc bắt buộc

- **UI dùng XML View + ViewBinding. Không dùng Jetpack Compose** (không thêm plugin, dependency hay file Compose).
- Kiến trúc **Clean Architecture** cho phân lớp, **MVI** cho presentation.
- DI bằng **Hilt**. Bất đồng bộ bằng **Coroutines/Flow** (không dùng RxJava, LiveData).
- Điều hướng bằng **Navigation Component** với `res/navigation/nav_graph.xml`, single activity (`MainActivity`).
- Version thư viện chỉ khai báo trong `gradle/libs.versions.toml`, không hard-code trong `build.gradle.kts`.
- Text, màu, kích thước để trong `res/values` (`strings.xml`, `colors.xml`, `dimens.xml`), không hard-code trong layout/code.

## Cấu trúc package

```
com.emojibattery.app
├── core
│   ├── base         # BaseActivity, BaseFragment (ViewBinding)
│   ├── mvi          # UiState, UiIntent, UiEffect, MviViewModel
│   ├── extension    # collectWhenStarted, ...
│   └── di           # Dispatcher qualifiers (@IoDispatcher, @DefaultDispatcher)
├── domain           # Kotlin thuần, KHÔNG import android.*
│   ├── model
│   ├── repository   # interface
│   └── usecase      # mỗi use case một class, `operator fun invoke`
├── data
│   ├── local        # DataStore, database...
│   ├── remote       # (khi có API)
│   ├── repository   # XxxRepositoryImpl implements domain repository
│   └── di           # Hilt module: @Binds repository, @Provides DataStore...
└── presentation
    ├── main         # MainActivity (NavHostFragment)
    └── <feature>    # XxxContract, XxxViewModel, XxxFragment
```

Chiều phụ thuộc: `presentation → domain ← data`. `presentation` không gọi thẳng `data`.

## Thêm một màn hình mới (MVI)

1. `presentation/<feature>/XxxContract.kt`: `data class XxxState : UiState`, `sealed interface XxxIntent : UiIntent`, `sealed interface XxxEffect : UiEffect`.
2. `XxxViewModel`: `@HiltViewModel`, kế thừa `MviViewModel<XxxState, XxxIntent, XxxEffect>`, xử lý trong `handleIntent`, đổi state bằng `setState { copy(...) }`, sự kiện một lần (navigate, toast, dialog) bằng `sendEffect(...)`.
3. `res/layout/fragment_xxx.xml` và `XxxFragment`: `@AndroidEntryPoint`, kế thừa `BaseFragment<FragmentXxxBinding>(FragmentXxxBinding::inflate)`.
   - `initView()`: setup view, listener gửi intent `viewModel.onIntent(...)`.
   - `observe()`: `collectWhenStarted(viewModel.state, ::render)` và `collectWhenStarted(viewModel.effect) { ... }`.
   - `render(state)` chỉ đọc state và set view, không có logic nghiệp vụ.
4. Thêm `<fragment>` và `<action>` vào `nav_graph.xml`.
5. Logic cần data: thêm use case ở `domain/usecase`, interface ở `domain/repository`, implementation ở `data/repository`, bind trong `data/di`.

Ví dụ hoàn chỉnh: `presentation/splash/`.

## Đặt tên

- Layout: `activity_xxx.xml`, `fragment_xxx.xml`, `item_xxx.xml`, `dialog_xxx.xml`.
- View id: snake_case có tiền tố loại view: `tv_`, `iv_`, `btn_`, `rv_`, `progress_`...
- Drawable: `bg_`, `ic_`, `img_`. Màu: theo tên ngữ nghĩa (`brand_purple`), không theo mã hex.

## Lệnh

```
./gradlew assembleDebug        # build APK debug
./gradlew testDebugUnitTest    # unit test
./gradlew lint                 # Android lint
```

Unit test đặt ở `app/src/test`, ưu tiên test use case và ViewModel với fake repository + `kotlinx-coroutines-test`.

## Ghi chú

- Môi trường Claude trên cloud không tải được Android SDK nên không build được ở đó; cần build trong Android Studio.
- Splash hiện là bản tạm, sẽ chỉnh cho khớp Figma khi có quyền xem file thiết kế.
