# CLAUDE.md - Developer Profile Feature

## Purpose

Displays a GitHub developer/user profile. Shows user info (avatar, bio, stats), their repositories with filtering and sorting, and follower/following counts. Reached by clicking on a developer's name from any repository card.

## Module Structure

```
feature/dev-profile/
├── domain/
│   ├── model/
│   │   ├── DeveloperProfile.kt       # User profile data model
│   │   ├── DeveloperRepository.kt    # User's repository model
│   │   ├── RepoFilterType.kt         # Filter: All, Sources, Forks, etc.
│   │   └── RepoSortType.kt           # Sort: Stars, Name, Updated, etc.
│   └── repository/DeveloperProfileRepository.kt  # Profile + repos
├── data/
│   ├── di/SharedModule.kt            # Koin: devProfileModule
│   ├── repository/DeveloperProfileRepositoryImpl.kt
│   ├── dto/                           # Network DTOs
│   └── mappers/                       # DTO → domain model mappers
└── presentation/
    ├── DeveloperProfileViewModel.kt   # Profile loading, repo filtering/sorting
    ├── DeveloperProfileState.kt       # profile, repos, filters, loading
    ├── DeveloperProfileAction.kt      # Load, filter, sort, click actions
    ├── DeveloperProfileEvent.kt       # One-off events
    ├── DeveloperProfileRoot.kt        # Main composable
    └── components/                    # Profile header, repo list, filter controls
```

## Key Interfaces

```kotlin
interface DeveloperProfileRepository {
    suspend fun getDeveloperProfile(username: String): Result<DeveloperProfile>
    suspend fun getDeveloperRepositories(username: String): Result<List<DeveloperRepository>>
}
```

## Navigation

Route: `KiriStoreGraph.DeveloperProfileScreen(username: String)`

## Implementation Notes

- Profile and repos are fetched in parallel on load
- Client-side filtering by `RepoFilterType` (All, Sources, Forks) and sorting by `RepoSortType` (Stars, Name, Updated)
- Both API calls return `Result<T>` for error handling
- Reached from repository cards throughout the app (home, search, details, favourites, starred)

