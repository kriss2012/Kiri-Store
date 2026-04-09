# CLAUDE.md - Apps Feature

## Purpose

Manages installed applications. Lists all apps installed through Kiri Store, allows launching them, and checks for available updates. Primarily relevant on **Android** (apps section is hidden on Desktop).

## Module Structure

```
feature/apps/
├── domain/
│   └── repository/AppsRepository.kt   # Installed apps, launch, update check
├── data/
│   ├── di/SharedModule.kt            # Koin: appsModule
│   └── repository/AppsRepositoryImpl.kt  # Implementation using core InstalledAppsRepository
└── presentation/
    ├── AppsViewModel.kt               # State management for installed apps list
    ├── AppsState.kt                   # apps list, loading, error
    ├── AppsAction.kt                  # Refresh, OpenApp, CheckUpdates, clicks
    ├── AppsEvent.kt                   # One-off events
    ├── AppsRoot.kt                    # Main composable (apps list)
    └── components/                    # App item cards, update badges
```

## Key Interfaces

```kotlin
interface AppsRepository {
    suspend fun getApps(): Flow<List<InstalledApp>>
    suspend fun openApp(installedApp: InstalledApp, onCantLaunchApp: () -> Unit = {})
    suspend fun getLatestRelease(owner: String, repo: String): GithubRelease?
}
```

## Navigation

Route: `KiriStoreGraph.AppsScreen` (data object, no params)

## Implementation Notes

- Uses `InstalledAppsRepository` and `SyncInstalledAppsUseCase` from core/domain
- `openApp()` uses `AppLauncher` from core/domain to launch the installed app
- `getLatestRelease()` checks if a newer version is available
- Platform-specific: `PackageMonitor` and `Installer` handle Android package management
- The apps section in the home screen bottom nav is only visible on `Platform.ANDROID`

