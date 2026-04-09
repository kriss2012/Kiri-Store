package zed.rainxch.kiristore.app.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import zed.rainxch.kiristore.MainViewModel

val mainModule: Module =
    module {
        viewModel {
            MainViewModel(
                tweaksRepository = get(),
                installedAppsRepository = get(),
                rateLimitRepository = get(),
                syncUseCase = get(),
                authenticationState = get(),
            )
        }
    }
