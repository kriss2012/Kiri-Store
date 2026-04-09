package zed.rainxch.kiristore.app.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import zed.rainxch.apps.presentation.AppsViewModel
import zed.rainxch.auth.presentation.AuthenticationViewModel
import zed.rainxch.details.presentation.DetailsViewModel
import zed.rainxch.devprofile.presentation.DeveloperProfileViewModel
import zed.rainxch.favourites.presentation.FavouritesViewModel
import zed.rainxch.home.presentation.HomeViewModel
import zed.rainxch.profile.presentation.ProfileViewModel
import zed.rainxch.recentlyviewed.presentation.RecentlyViewedViewModel
import zed.rainxch.search.presentation.SearchViewModel
import zed.rainxch.starred.presentation.StarredReposViewModel
import zed.rainxch.tweaks.presentation.TweaksViewModel

val viewModelsModule =
    module {
        viewModelOf(::AppsViewModel)
        viewModelOf(::AuthenticationViewModel)
        viewModelOf(::DetailsViewModel)
        viewModelOf(::DeveloperProfileViewModel)
        viewModelOf(::FavouritesViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::RecentlyViewedViewModel)
        viewModelOf(::SearchViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::TweaksViewModel)
        viewModelOf(::StarredReposViewModel)
    }

