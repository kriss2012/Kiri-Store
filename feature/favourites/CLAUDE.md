# CLAUDE.md - Favourites Feature

## Purpose

Displays the user's locally saved favorite repositories. This is a **presentation-only** feature with no domain or data layer -- it uses `FavouritesRepository` from `core/domain` directly.

## Module Structure

```
feature/favourites/
└── presentation/
    ├── FavouritesViewModel.kt         # Observes favourites, handles remove
    ├── FavouritesState.kt             # favourites list, loading
    ├── FavouritesAction.kt            # RemoveFavourite, click actions
    ├── FavouritesRoot.kt              # Main composable (list of favourites)
    ├── model/FavouriteRepository.kt   # UI model for display
    ├── mappers/FavouriteRepositoryMapper.kt  # Domain → UI model mapper
    └── components/FavouriteRepositoryItem.kt  # Individual favourite card
```

## Key Dependencies

- `FavouritesRepository` (from `core/domain`) - CRUD operations for favourites
- Favourites are stored locally in Room database (`FavoriteRepoDao` in `core/data`)

## Navigation

Route: `KiriStoreGraph.FavouritesScreen` (data object, no params)

## Implementation Notes

- No network calls -- all data is local (Room database)
- Uses a presentation-layer `FavouriteRepository` UI model mapped from the domain `FavoriteRepo`
- Adding to favourites happens in other features (home, details, search); this feature only displays and removes
- The Koin module for this feature is registered in `composeApp/.../app/di/ViewModelsModule.kt` since there's no `data/di/` layer

