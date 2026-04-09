# CLAUDE.md - Home Feature

## Purpose

Main discovery screen of the app. Displays repositories in three categories: **Trending**, **Hot Releases**, and **Most Popular**. Supports infinite-scroll pagination and integrates with installed apps, favourites, and starred status.

## Module Structure

```
feature/home/
├── domain/
│   ├── model/HomeCategory.kt          # Enum: TRENDING, HOT_RELEASE, MOST_POPULAR
│   └── repository/HomeRepository.kt   # Paginated flows per category
├── data/
│   ├── di/SharedModule.kt             # Koin: homeModule
│   ├── repository/HomeRepositoryImpl.kt  # GitHub API calls with caching & pagination
│   ├── data_source/CachedRepositoriesDataSource.kt  # Per-category cache (7-day expiry)
│   ├── dto/                           # Network DTOs
│   └── mappers/                       # DTO → domain model mappers
└── presentation/
    ├── HomeViewModel.kt               # State management, pagination logic
    ├── HomeState.kt                   # repos, isLoading, category, hasMorePages, etc.
    ├── HomeAction.kt                  # Refresh, Retry, LoadMore, SwitchCategory, clicks
    ├── HomeEvent.kt                   # OnScrollToListTop
    ├── HomeRoot.kt                    # Main composable (staggered grid + filter chips)
    ├── components/HomeFilterChips.kt  # Category filter chip row
    ├── locals/LocalHomeTopBarLiquid.kt
    └── utils/HomeCategoryMapper.kt    # Map HomeCategory to display strings
```

## Key Interfaces

```kotlin
interface HomeRepository {
    fun getTrendingRepositories(page: Int): Flow<PaginatedDiscoveryRepositories>
    fun getHotReleaseRepositories(page: Int): Flow<PaginatedDiscoveryRepositories>
    fun getMostPopular(page: Int): Flow<PaginatedDiscoveryRepositories>
}
```

## ViewModel Dependencies

`HomeViewModel` depends on: `HomeRepository`, `InstalledAppsRepository`, `Platform`, `SyncInstalledAppsUseCase`, `FavouritesRepository`, `StarredRepository`, `KiriStoreLogger`

## Navigation

Route: `KiriStoreGraph.HomeScreen` (data object, no params)

## Implementation Notes

- Uses `Semaphore` in `HomeRepositoryImpl` for concurrent request control
- Cache is per-category with 7-day TTL in `CachedRepositoriesDataSource`
- Pagination uses `nextPageIndex` tracking; deduplicates by `fullName`
- Apps section visibility is platform-dependent (`Platform.ANDROID` only)
- Observes installed apps, favourites, and starred repos reactively to update status badges
- State uses `onStart` + `stateIn(WhileSubscribed)` for lazy initialization

