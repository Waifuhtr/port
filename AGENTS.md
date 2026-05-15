# Repository Guidelines

## Project Structure & Module Organization

Ludens is a Kotlin Multiplatform (Compose) wrapper that ports RPG Maker MV/MZ games to mobile via
WebView.

```
composeApp/src/
├── commonMain/kotlin/com/yoimerdr/compose/ludens/
│   ├── app/          # Theme, navigation, DI (Koin) wiring
│   ├── core/         # Domain models, ports, platform abstractions (expect/actual)
│   ├── features/     # Feature modules (home, settings)
│   └── ui/           # Reusable UI components
├── androidMain/      # Android-specific implementations
└── iosMain/          # iOS-specific implementations

build-logic/          # Custom Gradle plugins (resource sync, manifest generation)
project/              # Game assets (www/) and static resources
docs/                 # Astro Starlight documentation site
resources/            # RPG Maker plugins (e.g., YDP_Ludens.js)
```

Key configuration files live at the repository root:

- `ludens.properties` — App identity, manifest flags, permissions, settings presets.
- `keystore.properties` — Release signing credentials (**do not commit**).
- `gradle.properties` — Kotlin/Gradle build-system options.

## Approach
- Think before acting. Read existing files before writing code.
- Be concise in output but thorough in reasoning.
- Prefer editing over rewriting whole files.
- Do not re-read files you have already read unless the file may have changed.
- Skip files over 100KB unless explicitly required.
- Suggest running /cost when a session is running long to monitor cache ratio.
- Recommend starting a new session when switching to an unrelated task.
- Test your code before declaring done.
- No sycophantic openers or closing fluff.
- Keep solutions simple and direct.
- User instructions always override this file.


## Build & Development Commands

| Command                                                       | Description                                                 |
|---------------------------------------------------------------|-------------------------------------------------------------|
| `./gradlew assembleDebug`                                     | Build a debug APK                                           |
| `./gradlew assembleRelease`                                   | Build a signed release APK (requires `keystore.properties`) |
| `./gradlew :composeApp:compileDebugKotlinAndroid --no-daemon` | is a focused Android Kotlin/Compose compile check.          |
| `./gradlew :composeApp:testDebugUnitTest`                     | runs Android unit tests when test sources are present.      |
| `./gradlew clean`                                             | Remove all build artifacts                                  |

**Prerequisites:** Android Studio Otter 2 Feature Drop (2025.2.2)+, JDK 17+.

Debug APK output: `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

## Coding Style & Naming Conventions

- Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html) (
  `kotlin.code.style=official`).
- Use `expect`/`actual` declarations for platform-specific code.
- Prefer immutable state and pure functions.
- Wire dependencies through Koin modules — follow existing module patterns.
- Match the style of surrounding code; when in doubt, look at implementations in the same package.

## Testing Guidelines

Use `kotlin.test` for shared and platform tests. Put shared tests in
`composeApp/src/commonTest/kotlin/`; use platform test source sets only for platform-specific
behavior. There is no documented coverage gate, so focus on regression tests and run the narrowest
relevant Gradle test task plus a compile/build check.

## Core Design Rules

- **Binary Rhythm:** UI sections alternate between Pure Black (`#000000`) and Light Gray (`#F5F5F7`)
  using `colorScheme.background` and `colorScheme.surfaceContainerLowest`.
- **Strict Chromatic Accent:** Use Ludens Primary Blue (`#0071E3` mapped to `colorScheme.primary`) *
  *exclusively** for interactive elements (buttons, links, active states). No other accent colors
  should be introduced.
- **Typography:**
    - **Headings (20sp+):** `Plus Jakarta Sans`. Use tight line-heights and negative letter-spacing
      for billboard-like impact.
    - **Body (<20sp):** `Inter`.
- **Shapes & Radii:**
    - Use `LocalRadius.current.standard` (8.dp) for standard buttons.
    - Use `LocalRadius.current.comfortable` (11.dp) for cards/containers.
- **Spacing:** Avoid hardcoded `dp` values. Always use `LocalSpacing.current.*` (e.g., `medium` for
  14.dp standard padding).
- **File Sensitivity:** Always ensure exact case matching for asset references, as Android and iOS
  file systems are case-sensitive unlike Windows.

## Commit & Pull Request Guidelines

### Commit Messages

Use [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>: <brief description>
```

Types: `feat`, `fix`, `refactor`, `docs`, `chore`, `test`, `style`.

### Pull Requests

- Branch from `develop` using descriptive names (`feat/…`, `fix/…`, `docs/…`).
- Target the `develop` branch (PRs to `main` are release-only).
- Ensure `./gradlew assembleDebug` passes before submitting.
- Update `CHANGELOG.md` under `[Unreleased]` and relevant docs in `docs/`.
- Fill out the PR template with testing steps and platform verification.

## Architecture Overview

| Layer       | Responsibility                                              |
|-------------|-------------------------------------------------------------|
| `app/`      | Theme, navigation graph, Koin module aggregation            |
| `core/`     | Domain models, repository interfaces, platform abstractions |
| `features/` | Feature-scoped screens and view models (home, settings)     |
| `ui/`       | Shared components (buttons, dropdowns, layout primitives)   |

Key dependencies: **Koin 4.x** (DI), **Proto DataStore** (settings), **compose-webview-multiplatform
** (game rendering), **compose-virtualjoystick** (controls), **AndroidX Navigation Compose**.

## Security & Configuration Tips

Do not commit local secrets or machine-specific files such as `keystore.properties` or
`local.properties`; use `keystore.properties.template` as the signing reference. App identity,
manifest flags, permissions, language/font sync, and settings presets are configured through
`ludens.properties`. Preserve exact RPG Maker asset casing because Android and iOS filesystems are
case-sensitive.