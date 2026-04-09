# CLAUDE.md - Auth Feature

## Purpose

GitHub OAuth authentication using the **device flow**. Users authenticate by visiting a URL and entering a code displayed in the app. No browser redirect needed, making it suitable for both Android and Desktop.

## Module Structure

```
feature/auth/
├── domain/
│   └── repository/AuthenticationRepository.kt  # Device flow interface
├── data/
│   ├── di/SharedModule.kt            # Koin: authModule
│   ├── repository/AuthenticationRepositoryImpl.kt  # OAuth device flow implementation
│   └── network/GitHubAuthApi.kt      # GitHub OAuth API endpoints
└── presentation/
    ├── AuthenticationViewModel.kt     # Manages device flow lifecycle
    ├── AuthenticationState.kt         # Code, URL, loading, error
    ├── AuthenticationAction.kt        # StartAuth, Cancel, etc.
    ├── AuthenticationEvent.kt         # One-off events
    ├── AuthenticationRoot.kt          # UI: displays code + verification URL
    └── components/                    # Auth UI components
```

## Key Interfaces

```kotlin
interface AuthenticationRepository {
    val accessTokenFlow: Flow<String?>
    suspend fun startDeviceFlow(): GithubDeviceStart
    suspend fun awaitDeviceToken(start: GithubDeviceStart): GithubDeviceTokenSuccess
}
```

## Navigation

Route: `KiriStoreGraph.AuthenticationScreen` (data object, no params)

## Implementation Notes

- Uses GitHub's [device authorization flow](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps#device-flow)
- `startDeviceFlow()` returns a user code + verification URL to display
- `awaitDeviceToken()` polls GitHub until the user completes verification
- Token is stored via `TokenStore` in core/data (DataStore-backed)
- `GITHUB_CLIENT_ID` must be set in `local.properties` for builds
- `accessTokenFlow` is observed app-wide by `MainViewModel` for auth state

