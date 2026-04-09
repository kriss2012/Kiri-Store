# CLAUDE.md - Starred Feature

## Purpose

Displays the user's locally saved starred repositories. This is a **presentation-only** feature with no domain or data layer -- it uses `StarredRepository` from `core/domain` directly.

## Module Structure

```
feature/starred/
└── presentation/
    ├── StarredReposViewModel.kt       # Observes starred repos, handles remove
    ├── StarredReposState.kt           # starred list, loading
    ├── StarredReposAction.kt          # RemoveStarred, click actions
    ├── StarredReposRoot.kt            # Main composable (list of starred repos)
    ├── model/StarredRepositoryUi.kt   # UI model for display
    ├── mappers/StarredRepoToUiMapper.kt  # Domain → UI model mapper
    ├── utils/TimeFormatUtils.kt       # Time formatting utilities
    └── components/StarredRepositoryItem.kt  # Individual starred repo card
```

## Key Dependencies

- `StarredRepository` (from `core/domain`) - CRUD operations for starred repos
- Starred repos are stored locally in Room database (`StarredRepoDao` in `core/data`)

## Navigation

Route: `KiriStoreGraph.StarredReposScreen` (data object, no params)

## Implementation Notes

- No network calls -- all data is local (Room database)
- Uses a presentation-layer `StarredRepositoryUi` model mapped from the domain `StarredRepository` entity
- Starring happens in other features (home, details, search); this feature only displays and removes
- Includes its own `TimeFormatUtils` for formatting timestamps on starred items
- The Koin module for this feature is registered in `composeApp/.../app/di/ViewModelsModule.kt` since there's no `data/di/` layer

